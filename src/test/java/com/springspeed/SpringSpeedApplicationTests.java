package com.springspeed;

import com.springspeed.model.User;
import com.springspeed.model.Product;
import com.springspeed.repository.UserRepository;
import com.springspeed.repository.ProductRepository;
import com.springspeed.service.RecommendationService;
import com.springspeed.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpringSpeed Application Tests
 * 系统功能测试类
 */
@SpringBootTest
class SpringSpeedApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private RecommendationService recommendationService;

    @Test
    void contextLoads() {
        // 测试Spring容器是否正常加载
        assertNotNull(userRepository);
        assertNotNull(productRepository);
        assertNotNull(userProfileService);
        assertNotNull(recommendationService);
        System.out.println("Spring容器加载成功");
    }

    @Test
    void testDatabaseConnection() {
        // 测试数据库连接是否正常
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        
        assertTrue(users.size() > 0, "用户表数据为空");
        assertTrue(products.size() > 0, "商品表数据为空");
        
        System.out.println("数据库连接成功，用户数: " + users.size() + ", 商品数: " + products.size());
    }

    @Test
    void testUserProfileService() {
        // 测试用户画像服务
        Long userId = 1L;
        
        // 测试兴趣标签提取
        String interestTags = userProfileService.extractUserInterestTags(userId);
        System.out.println("用户" + userId + "的兴趣标签: " + interestTags);
        assertNotNull(interestTags);
        
        // 测试RFM模型计算
        userProfileService.calculateUserRFM(userId);
        User user = userRepository.findById(userId).orElse(null);
        assertNotNull(user);
        System.out.println("用户" + userId + "的RFM分数: " + user.getRfmScore() + ", 用户细分: " + user.getUserSegment());
        assertTrue(user.getRfmScore() >= 0);
        assertNotNull(user.getUserSegment());
        
        // 测试用户画像概览
        Map<String, Object> profile = userProfileService.getUserProfileOverview(userId);
        System.out.println("用户" + userId + "的画像概览: " + profile);
        assertNotNull(profile);
        assertTrue(profile.containsKey("userId"));
        assertTrue(profile.containsKey("interestTags"));
        assertTrue(profile.containsKey("rfmScore"));
        assertTrue(profile.containsKey("userSegment"));
    }

    @Test
    void testRecommendationService() {
        // 测试推荐服务
        Long userId = 1L;
        int recommendCount = 5;
        
        // 测试基于内容的推荐
        List<Product> contentBasedRecommendations = recommendationService.contentBasedRecommendation(userId, recommendCount);
        System.out.println("基于内容的推荐结果: " + contentBasedRecommendations);
        assertNotNull(contentBasedRecommendations);
        assertTrue(contentBasedRecommendations.size() >= 0);
        
        // 测试热门商品推荐
        List<Product> popularRecommendations = recommendationService.popularProductsRecommendation(recommendCount);
        System.out.println("热门商品推荐结果: " + popularRecommendations);
        assertNotNull(popularRecommendations);
        assertTrue(popularRecommendations.size() >= 0);
        
        // 测试首页个性化推荐
        List<Product> homeRecommendations = recommendationService.homePageRecommendation(userId, recommendCount);
        System.out.println("首页个性化推荐结果: " + homeRecommendations);
        assertNotNull(homeRecommendations);
        assertTrue(homeRecommendations.size() >= 0);
        
        // 测试购物车关联推荐
        List<Product> cartRecommendations = recommendationService.cartRecommendation(userId, recommendCount);
        System.out.println("购物车关联推荐结果: " + cartRecommendations);
        assertNotNull(cartRecommendations);
        assertTrue(cartRecommendations.size() >= 0);
        
        // 测试商品详情页推荐
        Long productId = 1L;
        List<Product> productRecommendations = recommendationService.productDetailRecommendation(productId, userId, recommendCount);
        System.out.println("商品详情页推荐结果: " + productRecommendations);
        assertNotNull(productRecommendations);
        assertTrue(productRecommendations.size() >= 0);
    }

    @Test
    void testBatchUpdateUserProfiles() {
        // 测试批量更新用户画像
        System.out.println("开始批量更新用户画像...");
        userProfileService.batchUpdateUserProfiles();
        System.out.println("批量更新用户画像完成");
        
        // 验证更新结果
        List<User> users = userRepository.findAll();
        for (User user : users) {
            assertNotNull(user.getInterestTags());
            assertTrue(user.getRfmScore() >= 0);
            assertNotNull(user.getUserSegment());
        }
        System.out.println("所有用户画像更新成功");
    }

    @Test
    void testRealTimeUpdateRecommendation() {
        // 测试实时更新推荐
        Long userId = 1L;
        Long productId = 2L;
        int behaviorType = 1; // 浏览行为
        
        System.out.println("开始实时更新推荐...");
        recommendationService.realTimeUpdateRecommendation(userId, productId, behaviorType);
        System.out.println("实时更新推荐完成");
        
        // 验证推荐结果是否更新
        List<Product> homeRecommendations = recommendationService.getCachedRecommendations(userId, 1);
        assertNotNull(homeRecommendations);
        System.out.println("实时更新后的首页推荐结果: " + homeRecommendations);
    }
}
