package com.springspeed.controller;

import com.springspeed.model.Product;
import com.springspeed.service.RecommendationService;
import com.springspeed.service.RecommendationEffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Recommendation Controller
 * 推荐系统控制器
 */
@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RecommendationEffectService recommendationEffectService;

    /**
     * 首页个性化推荐
     */
    @GetMapping("/home/{userId}")
    public ResponseEntity<List<Product>> getHomeRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int count) {
        List<Product> recommendations = recommendationService.homePageRecommendation(userId, count);
        
        // 记录曝光
        for (Product product : recommendations) {
            recommendationEffectService.recordExposure(userId, product.getId(), 1);
        }
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 购物车关联推荐
     */
    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<Product>> getCartRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int count) {
        List<Product> recommendations = recommendationService.cartRecommendation(userId, count);
        
        // 记录曝光
        for (Product product : recommendations) {
            recommendationEffectService.recordExposure(userId, product.getId(), 2);
        }
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 商品详情页猜你喜欢
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Product>> getProductRecommendations(
            @PathVariable Long productId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "10") int count) {
        List<Product> recommendations = recommendationService.productDetailRecommendation(productId, userId, count);
        
        // 记录曝光
        if (userId != null) {
            for (Product product : recommendations) {
                recommendationEffectService.recordExposure(userId, product.getId(), 3);
            }
        }
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 热门商品推荐
     */
    @GetMapping("/popular")
    public ResponseEntity<List<Product>> getPopularProducts(
            @RequestParam(defaultValue = "20") int count) {
        List<Product> recommendations = recommendationService.popularProductsRecommendation(count);
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 基于内容的推荐
     */
    @GetMapping("/content-based/{userId}")
    public ResponseEntity<List<Product>> getContentBasedRecommendations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int count) {
        List<Product> recommendations = recommendationService.contentBasedRecommendation(userId, count);
        
        // 记录曝光
        for (Product product : recommendations) {
            recommendationEffectService.recordExposure(userId, product.getId(), 4);
        }
        
        return ResponseEntity.ok(recommendations);
    }

    /**
     * 记录推荐点击
     */
    @PostMapping("/click")
    public ResponseEntity<Void> recordRecommendationClick(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int recommendType) {
        recommendationEffectService.recordClick(userId, productId, recommendType);
        return ResponseEntity.ok().build();
    }

    /**
     * 记录推荐购买
     */
    @PostMapping("/purchase")
    public ResponseEntity<Void> recordRecommendationPurchase(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int recommendType,
            @RequestParam double revenue) {
        recommendationEffectService.recordPurchase(userId, productId, recommendType, revenue);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取推荐效果统计
     */
    @GetMapping("/effect/statistics")
    public ResponseEntity<Map<String, Object>> getEffectStatistics(
            @RequestParam int recommendType,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {
        if (startDate == null) {
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); // 默认7天前
        }
        if (endDate == null) {
            endDate = new Date();
        }
        
        Map<String, Object> statistics = recommendationEffectService.getEffectStatisticsByType(recommendType, startDate, endDate);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 获取所有推荐类型的效果统计
     */
    @GetMapping("/effect/all-statistics")
    public ResponseEntity<List<Map<String, Object>>> getAllEffectStatistics(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {
        if (startDate == null) {
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); // 默认7天前
        }
        if (endDate == null) {
            endDate = new Date();
        }
        
        List<Map<String, Object>> statistics = recommendationEffectService.getAllEffectStatistics(startDate, endDate);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 获取商品推荐效果排名
     */
    @GetMapping("/effect/product-ranking")
    public ResponseEntity<List<Map<String, Object>>> getProductEffectRanking(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "10") int limit) {
        if (startDate == null) {
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); // 默认7天前
        }
        if (endDate == null) {
            endDate = new Date();
        }
        
        List<Map<String, Object>> ranking = recommendationEffectService.getProductEffectRanking(startDate, endDate, limit);
        return ResponseEntity.ok(ranking);
    }

    /**
     * 获取用户推荐效果统计
     */
    @GetMapping("/effect/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserEffectStatistics(
            @PathVariable Long userId,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {
        if (startDate == null) {
            startDate = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); // 默认7天前
        }
        if (endDate == null) {
            endDate = new Date();
        }
        
        Map<String, Object> statistics = recommendationEffectService.getUserEffectStatistics(userId, startDate, endDate);
        return ResponseEntity.ok(statistics);
    }
}
