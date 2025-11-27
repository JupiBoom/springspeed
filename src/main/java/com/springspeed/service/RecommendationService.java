package com.springspeed.service;

import com.springspeed.model.Product;
import com.springspeed.model.RecommendationResult;
import java.util.List;

/**
 * Recommendation Service Interface
 * 推荐服务接口
 */
public interface RecommendationService {

    /**
     * 基于内容的推荐
     * 根据用户兴趣标签匹配商品属性
     */
    List<Product> contentBasedRecommendation(Long userId, int count);

    /**
     * 热门商品推荐
     * 根据商品的销量、点击量等计算热门商品
     */
    List<Product> popularProductsRecommendation(int count);

    /**
     * 首页个性化推荐
     * 综合多种推荐算法的结果
     */
    List<Product> homePageRecommendation(Long userId, int count);

    /**
     * 购物车关联推荐
     * 根据购物车中的商品推荐相关商品
     */
    List<Product> cartRecommendation(Long userId, int count);

    /**
     * 商品详情页猜你喜欢
     * 根据当前商品推荐相似商品
     */
    List<Product> productDetailRecommendation(Long productId, Long userId, int count);

    /**
     * 实时更新推荐结果
     * 根据用户最新行为动态调整推荐
     */
    void realTimeUpdateRecommendation(Long userId, Long productId, int behaviorType);

    /**
     * 保存推荐结果到数据库
     */
    void saveRecommendationResult(Long userId, int recommendType, List<Product> products);

    /**
     * 获取缓存的推荐结果
     */
    List<Product> getCachedRecommendations(Long userId, int recommendType);

    /**
     * 清除过期的推荐结果
     */
    void cleanExpiredRecommendations();
}
