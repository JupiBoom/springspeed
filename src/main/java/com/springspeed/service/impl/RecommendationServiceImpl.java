package com.springspeed.service.impl;

import com.springspeed.model.Product;
import com.springspeed.model.RecommendationResult;
import com.springspeed.model.User;
import com.springspeed.model.UserBehavior;
import com.springspeed.repository.ProductRepository;
import com.springspeed.repository.RecommendationResultRepository;
import com.springspeed.repository.UserRepository;
import com.springspeed.repository.UserBehaviorRepository;
import com.springspeed.service.RecommendationService;
import com.springspeed.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Recommendation Service Implementation
 * 推荐服务实现类
 */
@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    @Autowired
    private RecommendationResultRepository recommendationResultRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${recommendation.default-count}")
    private int defaultRecommendCount;

    @Value("${recommendation.popular-count}")
    private int popularRecommendCount;

    @Value("${recommendation.cache-expire}")
    private int cacheExpireSeconds;

    private static final String CACHE_KEY_RECOMMENDATION = "recommendation:%d:%d";
    private static final String CACHE_KEY_POPULAR_PRODUCTS = "popular_products";

    /**
     * 基于内容的推荐
     * 根据用户兴趣标签匹配商品属性
     */
    @Override
    public List<Product> contentBasedRecommendation(Long userId, int count) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getInterestTags() == null) {
            return popularProductsRecommendation(count);
        }

        // 获取用户兴趣标签
        List<String> userTags = Arrays.asList(user.getInterestTags().split(","));
        if (userTags.isEmpty()) {
            return popularProductsRecommendation(count);
        }

        // 获取所有上架商品
        List<Product> allProducts = productRepository.findByStatus(Product.STATUS_ACTIVE);
        if (allProducts.isEmpty()) {
            return Collections.emptyList();
        }

        // 计算商品与用户兴趣的匹配度
        Map<Product, Double> productScoreMap = new HashMap<>();
        for (Product product : allProducts) {
            if (product.getTags() == null) {
                continue;
            }

            List<String> productTags = Arrays.asList(product.getTags().split(","));
            double score = calculateTagSimilarity(userTags, productTags);
            if (score > 0) {
                productScoreMap.put(product, score);
            }
        }

        // 按匹配度排序，取前count个商品
        List<Product> recommendedProducts = productScoreMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果推荐结果不足，用热门商品补充
        if (recommendedProducts.size() < count) {
            List<Product> popularProducts = popularProductsRecommendation(count - recommendedProducts.size());
            recommendedProducts.addAll(popularProducts);
        }

        return recommendedProducts;
    }

    /**
     * 计算标签相似度（Jaccard系数）
     */
    private double calculateTagSimilarity(List<String> tags1, List<String> tags2) {
        Set<String> set1 = new HashSet<>(tags1);
        Set<String> set2 = new HashSet<>(tags2);

        if (set1.isEmpty() || set2.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    /**
     * 热门商品推荐
     * 根据商品的销量、点击量等计算热门商品
     */
    @Override
    public List<Product> popularProductsRecommendation(int count) {
        // 从缓存获取热门商品
        String cacheKey = CACHE_KEY_POPULAR_PRODUCTS;
        List<Product> popularProducts = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (popularProducts != null && !popularProducts.isEmpty()) {
            return popularProducts.stream().limit(count).collect(Collectors.toList());
        }

        // 缓存不存在时，从数据库计算
        List<Product> allProducts = productRepository.findByStatus(Product.STATUS_ACTIVE);
        if (allProducts.isEmpty()) {
            return Collections.emptyList();
        }

        // 计算商品热度得分
        Map<Product, Double> productHotScoreMap = new HashMap<>();
        for (Product product : allProducts) {
            double score = calculateProductHotScore(product);
            productHotScoreMap.put(product, score);
        }

        // 按热度排序
        popularProducts = productHotScoreMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 缓存热门商品
        redisTemplate.opsForValue().set(cacheKey, popularProducts, cacheExpireSeconds);

        return popularProducts;
    }

    /**
     * 计算商品热度得分
     */
    private double calculateProductHotScore(Product product) {
        double baseScore = 0.0;

        // 销量权重
        if (product.getSalesCount() > 0) {
            baseScore += product.getSalesCount() * 0.5;
        }

        // 浏览量权重
        if (product.getClickCount() > 0) {
            baseScore += product.getClickCount() * 0.3;
        }

        // 评分权重
        if (product.getAverageRating() > 0) {
            baseScore += product.getAverageRating() * 10;
        }

        // 上架时间权重（越新权重越高）
        long daysSincePublish = (new Date().getTime() - product.getCreatedTime().getTime()) / (1000 * 60 * 60 * 24);
        if (daysSincePublish <= 7) {
            baseScore *= 2.0; // 7天内新品加倍
        } else if (daysSincePublish <= 30) {
            baseScore *= 1.5; // 30天内新品1.5倍
        }

        return baseScore;
    }

    /**
     * 首页个性化推荐
     * 综合多种推荐算法的结果
     */
    @Override
    public List<Product> homePageRecommendation(Long userId, int count) {
        // 尝试从缓存获取
        String cacheKey = String.format(CACHE_KEY_RECOMMENDATION, userId, RecommendationResult.RECOMMEND_TYPE_HOME);
        List<Product> recommendations = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (recommendations != null && !recommendations.isEmpty()) {
            return recommendations;
        }

        // 个性化推荐（基于内容）
        List<Product> contentBasedRecommendations = contentBasedRecommendation(userId, count);

        // 保存到缓存
        redisTemplate.opsForValue().set(cacheKey, contentBasedRecommendations, cacheExpireSeconds);

        // 保存到数据库
        saveRecommendationResult(userId, RecommendationResult.RECOMMEND_TYPE_HOME, contentBasedRecommendations);

        return contentBasedRecommendations;
    }

    /**
     * 购物车关联推荐
     * 根据购物车中的商品推荐相关商品
     */
    @Override
    public List<Product> cartRecommendation(Long userId, int count) {
        // 尝试从缓存获取
        String cacheKey = String.format(CACHE_KEY_RECOMMENDATION, userId, RecommendationResult.RECOMMEND_TYPE_CART);
        List<Product> recommendations = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (recommendations != null && !recommendations.isEmpty()) {
            return recommendations;
        }

        // 获取用户购物车中的商品
        List<UserBehavior> cartBehaviors = userBehaviorRepository.findByUserIdAndBehaviorType(userId, UserBehavior.BEHAVIOR_ADD_TO_CART);
        if (cartBehaviors.isEmpty()) {
            return popularProductsRecommendation(count);
        }

        // 提取购物车商品的标签
        Set<String> cartTags = new HashSet<>();
        for (UserBehavior behavior : cartBehaviors) {
            Product product = behavior.getProduct();
            if (product != null && product.getTags() != null) {
                String[] tags = product.getTags().split(",");
                cartTags.addAll(Arrays.asList(tags));
            }
        }

        if (cartTags.isEmpty()) {
            return popularProductsRecommendation(count);
        }

        // 推荐具有相似标签的商品
        List<Product> allProducts = productRepository.findByStatus(Product.STATUS_ACTIVE);
        Map<Product, Double> productScoreMap = new HashMap<>();
        for (Product product : allProducts) {
            if (product.getTags() == null) {
                continue;
            }

            List<String> productTags = Arrays.asList(product.getTags().split(","));
            double score = calculateTagSimilarity(new ArrayList<>(cartTags), productTags);
            if (score > 0) {
                productScoreMap.put(product, score);
            }
        }

        // 按匹配度排序
        recommendations = productScoreMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果推荐结果不足，用热门商品补充
        if (recommendations.size() < count) {
            List<Product> popularProducts = popularProductsRecommendation(count - recommendations.size());
            recommendations.addAll(popularProducts);
        }

        // 保存到缓存
        redisTemplate.opsForValue().set(cacheKey, recommendations, cacheExpireSeconds);

        // 保存到数据库
        saveRecommendationResult(userId, RecommendationResult.RECOMMEND_TYPE_CART, recommendations);

        return recommendations;
    }

    /**
     * 商品详情页猜你喜欢
     * 根据当前商品推荐相似商品
     */
    @Override
    public List<Product> productDetailRecommendation(Long productId, Long userId, int count) {
        // 尝试从缓存获取
        String cacheKey = String.format(CACHE_KEY_RECOMMENDATION, productId, RecommendationResult.RECOMMEND_TYPE_PRODUCT);
        List<Product> recommendations = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (recommendations != null && !recommendations.isEmpty()) {
            return recommendations;
        }

        // 获取当前商品
        Product currentProduct = productRepository.findById(productId).orElse(null);
        if (currentProduct == null) {
            return popularProductsRecommendation(count);
        }

        // 获取当前商品的标签
        if (currentProduct.getTags() == null) {
            return popularProductsRecommendation(count);
        }

        List<String> currentProductTags = Arrays.asList(currentProduct.getTags().split(","));
        if (currentProductTags.isEmpty()) {
            return popularProductsRecommendation(count);
        }

        // 获取同分类的其他商品
        List<Product> sameCategoryProducts = productRepository.findByCategoryIdAndStatus(currentProduct.getCategoryId(), Product.STATUS_ACTIVE);
        if (sameCategoryProducts.isEmpty()) {
            return popularProductsRecommendation(count);
        }

        // 计算相似商品
        Map<Product, Double> productScoreMap = new HashMap<>();
        for (Product product : sameCategoryProducts) {
            if (product.getId().equals(productId) || product.getTags() == null) {
                continue;
            }

            List<String> productTags = Arrays.asList(product.getTags().split(","));
            double score = calculateTagSimilarity(currentProductTags, productTags);
            if (score > 0) {
                productScoreMap.put(product, score);
            }
        }

        // 按匹配度排序
        recommendations = productScoreMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果推荐结果不足，用热门商品补充
        if (recommendations.size() < count) {
            List<Product> popularProducts = popularProductsRecommendation(count - recommendations.size());
            recommendations.addAll(popularProducts);
        }

        // 保存到缓存
        redisTemplate.opsForValue().set(cacheKey, recommendations, cacheExpireSeconds);

        return recommendations;
    }

    /**
     * 实时更新推荐结果
     * 根据用户最新行为动态调整推荐
     */
    @Override
    public void realTimeUpdateRecommendation(Long userId, Long productId, int behaviorType) {
        // 更新用户画像
        userProfileService.updateUserProfile(userId);

        // 清除相关推荐缓存
        String homeCacheKey = String.format(CACHE_KEY_RECOMMENDATION, userId, RecommendationResult.RECOMMEND_TYPE_HOME);
        String cartCacheKey = String.format(CACHE_KEY_RECOMMENDATION, userId, RecommendationResult.RECOMMEND_TYPE_CART);
        redisTemplate.delete(homeCacheKey);
        redisTemplate.delete(cartCacheKey);

        // 异步重新计算推荐结果
        new Thread(() -> {
            try {
                homePageRecommendation(userId, defaultRecommendCount);
                cartRecommendation(userId, defaultRecommendCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 保存推荐结果到数据库
     */
    @Override
    public void saveRecommendationResult(Long userId, int recommendType, List<Product> products) {
        if (products.isEmpty()) {
            return;
        }

        // 删除用户该类型的旧推荐结果
        recommendationResultRepository.deleteByUserIdAndRecommendType(userId, recommendType);

        // 保存新的推荐结果
        Date now = new Date();
        List<RecommendationResult> results = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            RecommendationResult result = new RecommendationResult();
            result.setUserId(userId);
            result.setRecommendType(recommendType);
            result.setProductId(product.getId());
            result.setRecommendScore(1.0 - (i * 0.1)); // 排名越靠前分数越高
            result.setRankOrder(i + 1);
            result.setCreatedTime(now);
            result.setUpdatedTime(now);
            results.add(result);
        }

        recommendationResultRepository.saveAll(results);
    }

    /**
     * 获取缓存的推荐结果
     */
    @Override
    public List<Product> getCachedRecommendations(Long userId, int recommendType) {
        String cacheKey = String.format(CACHE_KEY_RECOMMENDATION, userId, recommendType);
        return (List<Product>) redisTemplate.opsForValue().get(cacheKey);
    }

    /**
     * 清除过期的推荐结果
     */
    @Override
    public void cleanExpiredRecommendations() {
        Date expireDate = new Date(System.currentTimeMillis() - cacheExpireSeconds * 1000);
        recommendationResultRepository.deleteByCreatedTimeBefore(expireDate);
    }
}
