package com.springspeed.service;

import com.springspeed.model.RecommendationEffect;
import com.springspeed.repository.RecommendationEffectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Recommendation Effect Analysis Service
 * 推荐效果分析服务
 */
@Service
@Transactional
public class RecommendationEffectService {

    @Autowired
    private RecommendationEffectRepository recommendationEffectRepository;

    /**
     * 记录推荐曝光
     */
    public void recordExposure(Long userId, Long productId, int recommendType) {
        RecommendationEffect effect = recommendationEffectRepository.findByUserIdAndProductIdAndRecommendType(userId, productId, recommendType);
        if (effect == null) {
            effect = new RecommendationEffect();
            effect.setUserId(userId);
            effect.setProductId(productId);
            effect.setRecommendType(recommendType);
            effect.setExposureTime(new Date());
            effect.setIsClicked(false);
            effect.setIsPurchased(false);
        }
        recommendationEffectRepository.save(effect);
    }

    /**
     * 记录推荐点击
     */
    public void recordClick(Long userId, Long productId, int recommendType) {
        RecommendationEffect effect = recommendationEffectRepository.findByUserIdAndProductIdAndRecommendType(userId, productId, recommendType);
        if (effect == null) {
            effect = new RecommendationEffect();
            effect.setUserId(userId);
            effect.setProductId(productId);
            effect.setRecommendType(recommendType);
            effect.setExposureTime(new Date());
        }
        effect.setClickTime(new Date());
        effect.setIsClicked(true);
        recommendationEffectRepository.save(effect);
    }

    /**
     * 记录推荐购买
     */
    public void recordPurchase(Long userId, Long productId, int recommendType, double revenue) {
        RecommendationEffect effect = recommendationEffectRepository.findByUserIdAndProductIdAndRecommendType(userId, productId, recommendType);
        if (effect == null) {
            effect = new RecommendationEffect();
            effect.setUserId(userId);
            effect.setProductId(productId);
            effect.setRecommendType(recommendType);
            effect.setExposureTime(new Date());
        }
        effect.setPurchaseTime(new Date());
        effect.setIsPurchased(true);
        effect.setRevenue(revenue);
        recommendationEffectRepository.save(effect);
    }

    /**
     * 获取推荐效果统计（按推荐类型）
     */
    public Map<String, Object> getEffectStatisticsByType(int recommendType, Date startDate, Date endDate) {
        List<RecommendationEffect> effects = recommendationEffectRepository.findByRecommendTypeAndExposureTimeBetween(recommendType, startDate, endDate);

        int totalExposures = effects.size();
        int totalClicks = (int) effects.stream().filter(RecommendationEffect::getIsClicked).count();
        int totalPurchases = (int) effects.stream().filter(RecommendationEffect::getIsPurchased).count();
        double totalRevenue = effects.stream().mapToDouble(RecommendationEffect::getRevenue).sum();

        double clickThroughRate = totalExposures > 0 ? (double) totalClicks / totalExposures : 0.0;
        double conversionRate = totalExposures > 0 ? (double) totalPurchases / totalExposures : 0.0;
        double clickConversionRate = totalClicks > 0 ? (double) totalPurchases / totalClicks : 0.0;
        double averageRevenuePerExposure = totalExposures > 0 ? totalRevenue / totalExposures : 0.0;

        Map<String, Object> statistics = new java.util.HashMap<>();
        statistics.put("recommendType", recommendType);
        statistics.put("totalExposures", totalExposures);
        statistics.put("totalClicks", totalClicks);
        statistics.put("totalPurchases", totalPurchases);
        statistics.put("totalRevenue", totalRevenue);
        statistics.put("clickThroughRate", clickThroughRate);
        statistics.put("conversionRate", conversionRate);
        statistics.put("clickConversionRate", clickConversionRate);
        statistics.put("averageRevenuePerExposure", averageRevenuePerExposure);

        return statistics;
    }

    /**
     * 获取所有推荐类型的效果统计
     */
    public List<Map<String, Object>> getAllEffectStatistics(Date startDate, Date endDate) {
        List<Integer> recommendTypes = List.of(
                RecommendationEffect.RECOMMEND_TYPE_HOME,
                RecommendationEffect.RECOMMEND_TYPE_CART,
                RecommendationEffect.RECOMMEND_TYPE_PRODUCT,
                RecommendationEffect.RECOMMEND_TYPE_CONTENT_BASED,
                RecommendationEffect.RECOMMEND_TYPE_POPULAR
        );

        return recommendTypes.stream()
                .map(type -> getEffectStatisticsByType(type, startDate, endDate))
                .collect(Collectors.toList());
    }

    /**
     * 获取商品推荐效果排名
     */
    public List<Map<String, Object>> getProductEffectRanking(Date startDate, Date endDate, int limit) {
        return recommendationEffectRepository.findProductEffectRanking(startDate, endDate, limit);
    }

    /**
     * 获取用户推荐效果统计
     */
    public Map<String, Object> getUserEffectStatistics(Long userId, Date startDate, Date endDate) {
        List<RecommendationEffect> effects = recommendationEffectRepository.findByUserIdAndExposureTimeBetween(userId, startDate, endDate);

        int totalExposures = effects.size();
        int totalClicks = (int) effects.stream().filter(RecommendationEffect::getIsClicked).count();
        int totalPurchases = (int) effects.stream().filter(RecommendationEffect::getIsPurchased).count();
        double totalRevenue = effects.stream().mapToDouble(RecommendationEffect::getRevenue).sum();

        double clickThroughRate = totalExposures > 0 ? (double) totalClicks / totalExposures : 0.0;
        double conversionRate = totalExposures > 0 ? (double) totalPurchases / totalExposures : 0.0;
        double clickConversionRate = totalClicks > 0 ? (double) totalPurchases / totalClicks : 0.0;

        Map<String, Object> statistics = new java.util.HashMap<>();
        statistics.put("userId", userId);
        statistics.put("totalExposures", totalExposures);
        statistics.put("totalClicks", totalClicks);
        statistics.put("totalPurchases", totalPurchases);
        statistics.put("totalRevenue", totalRevenue);
        statistics.put("clickThroughRate", clickThroughRate);
        statistics.put("conversionRate", conversionRate);
        statistics.put("clickConversionRate", clickConversionRate);

        return statistics;
    }

    /**
     * 清除过期的效果数据
     */
    public void cleanExpiredEffectData(int daysToKeep) {
        Date expireDate = new Date(System.currentTimeMillis() - daysToKeep * 24 * 60 * 60 * 1000);
        recommendationEffectRepository.deleteByExposureTimeBefore(expireDate);
    }
}
