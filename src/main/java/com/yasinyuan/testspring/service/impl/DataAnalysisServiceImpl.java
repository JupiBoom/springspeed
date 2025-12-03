package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.dao.ProductMapper;
import com.yasinyuan.testspring.dao.ProductSkuMapper;
import com.yasinyuan.testspring.service.DataAnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 数据分析服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataAnalysisServiceImpl implements DataAnalysisService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Override
    public List<Map<String, Object>> getProductSalesRanking(Integer topN, Date startTime, Date endTime) {
        // 默认查询前10条
        if (topN == null || topN <= 0) {
            topN = 10;
        }
        
        // 调用Mapper获取销售排行数据
        List<Map<String, Object>> rankingList = productMapper.selectProductSalesRanking(topN, startTime, endTime);
        
        // 格式化返回结果
        for (Map<String, Object> item : rankingList) {
            Long productId = (Long) item.get("productId");
            String productName = (String) item.get("productName");
            Integer salesCount = (Integer) item.get("salesCount");
            BigDecimal salesAmount = (BigDecimal) item.get("salesAmount");
            
            item.put("productId", productId);
            item.put("productName", productName);
            item.put("salesCount", salesCount != null ? salesCount : 0);
            item.put("salesAmount", salesAmount != null ? salesAmount : BigDecimal.ZERO);
        }
        
        return rankingList;
    }

    @Override
    public List<Map<String, Object>> getInventoryTurnoverAnalysis(Long categoryId, Long brandId, Date startTime, Date endTime) {
        List<Map<String, Object>> turnoverList = productMapper.selectInventoryTurnover(categoryId, brandId, startTime, endTime);
        
        // 计算库存周转率（周转率=销售成本/平均库存）
        for (Map<String, Object> item : turnoverList) {
            BigDecimal avgInventory = (BigDecimal) item.get("avgInventory");
            BigDecimal salesCost = (BigDecimal) item.get("salesCost");
            
            BigDecimal turnoverRate = BigDecimal.ZERO;
            if (avgInventory != null && avgInventory.compareTo(BigDecimal.ZERO) > 0 && salesCost != null) {
                turnoverRate = salesCost.divide(avgInventory, 4, BigDecimal.ROUND_HALF_UP);
            }
            
            item.put("turnoverRate", turnoverRate);
            item.put("avgInventory", avgInventory != null ? avgInventory : BigDecimal.ZERO);
            item.put("salesCost", salesCost != null ? salesCost : BigDecimal.ZERO);
        }
        
        return turnoverList;
    }

    @Override
    public Map<String, Object> getProductShelfEfficiency(Date startTime, Date endTime) {
        Map<String, Object> result = new HashMap<>();
        
        // 获取统计数据
        Map<String, Object> stats = productMapper.selectProductShelfStats(startTime, endTime);
        
        Long totalProducts = (Long) stats.get("totalProducts");
        Long publishedProducts = (Long) stats.get("publishedProducts");
        Long unpublishedProducts = (Long) stats.get("unpublishedProducts");
        Long avgPublishDuration = (Long) stats.get("avgPublishDuration");
        Long avgVerifyDuration = (Long) stats.get("avgVerifyDuration");
        
        result.put("totalProducts", totalProducts != null ? totalProducts : 0);
        result.put("publishedProducts", publishedProducts != null ? publishedProducts : 0);
        result.put("unpublishedProducts", unpublishedProducts != null ? unpublishedProducts : 0);
        result.put("avgPublishDuration", avgPublishDuration != null ? avgPublishDuration : 0);
        result.put("avgVerifyDuration", avgVerifyDuration != null ? avgVerifyDuration : 0);
        result.put("publishRate", totalProducts != null && totalProducts > 0 ? 
                new BigDecimal(publishedProducts).divide(new BigDecimal(totalProducts), 4, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
        
        return result;
    }

    @Override
    public List<Map<String, Object>> getProductSalesTrend(Long productId, Date startTime, Date endTime, String periodType) {
        if (periodType == null || !Arrays.asList("day", "week", "month").contains(periodType)) {
            periodType = "day";
        }
        
        return productMapper.selectProductSalesTrend(productId, startTime, endTime, periodType);
    }
}