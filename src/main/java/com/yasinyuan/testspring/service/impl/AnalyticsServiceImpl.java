package com.yasinyuan.testspring.service.impl;

import com.yasinyuan.testspring.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据分析Service实现类
 */
@Service("analyticsService")
public class AnalyticsServiceImpl implements AnalyticsService {
    
    @Override
    public List<Map<String, Object>> getProductSalesStats(Date startDate, Date endDate) {
        // 模拟数据，实际需要查询订单详情表获取销售数据
        List<Map<String, Object>> stats = new ArrayList<>();
        
        Map<String, Object> stat1 = new HashMap<>();
        stat1.put("productName", "iPhone 13 Pro");
        stat1.put("productSkuId", 1L);
        stat1.put("salesQuantity", 1250);
        stat1.put("salesAmount", 1250000.00);
        stat1.put("avgPrice", 1000.00);
        stats.add(stat1);
        
        Map<String, Object> stat2 = new HashMap<>();
        stat2.put("productName", "华为Mate 40 Pro");
        stat2.put("productSkuId", 2L);
        stat2.put("salesQuantity", 980);
        stat2.put("salesAmount", 735000.00);
        stat2.put("avgPrice", 750.00);
        stats.add(stat2);
        
        return stats;
    }
    
    @Override
    public Map<String, Object> getInventoryStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", 156);
        stats.put("totalStockQuantity", 25430);
        stats.put("totalStockAmount", 15678000.00);
        stats.put("warningProducts", 12);
        stats.put("overstockProducts", 8);
        stats.put("outOfStockProducts", 3);
        stats.put("totalWarehouses", 5);
        
        return stats;
    }
    
    @Override
    public List<Map<String, Object>> getCategorySalesStats(Date startDate, Date endDate) {
        List<Map<String, Object>> stats = new ArrayList<>();
        
        Map<String, Object> stat1 = new HashMap<>();
        stat1.put("categoryName", "手机");
        stat1.put("categoryId", 1L);
        stat1.put("salesQuantity", 3420);
        stat1.put("salesAmount", 3150000.00);
        stats.add(stat1);
        
        Map<String, Object> stat2 = new HashMap<>();
        stat2.put("categoryName", "电脑");
        stat2.put("categoryId", 2L);
        stat2.put("salesQuantity", 890);
        stat2.put("salesAmount", 1598000.00);
        stats.add(stat2);
        
        Map<String, Object> stat3 = new HashMap<>();
        stat3.put("categoryName", "家电");
        stat3.put("categoryId", 3L);
        stat3.put("salesQuantity", 1250);
        stat3.put("salesAmount", 987000.00);
        stats.add(stat3);
        
        return stats;
    }
    
    @Override
    public List<Map<String, Object>> getBrandSalesStats(Date startDate, Date endDate) {
        List<Map<String, Object>> stats = new ArrayList<>();
        
        Map<String, Object> stat1 = new HashMap<>();
        stat1.put("brandName", "Apple");
        stat1.put("brandId", 1L);
        stat1.put("salesQuantity", 2100);
        stat1.put("salesAmount", 2310000.00);
        stats.add(stat1);
        
        Map<String, Object> stat2 = new HashMap<>();
        stat2.put("brandName", "华为");
        stat2.put("brandId", 2L);
        stat2.put("salesQuantity", 1560);
        stat2.put("salesAmount", 1248000.00);
        stats.add(stat2);
        
        Map<String, Object> stat3 = new HashMap<>();
        stat3.put("brandName", "小米");
        stat3.put("brandId", 3L);
        stat3.put("salesQuantity", 1080);
        stat3.put("salesAmount", 648000.00);
        stats.add(stat3);
        
        return stats;
    }
    
    @Override
    public List<Map<String, Object>> getSalesTrend(String period, Date startDate, Date endDate) {
        List<Map<String, Object>> trend = new ArrayList<>();
        
        // 模拟最近7天的销售趋势
        for (int i = 6; i >= 0; i--) {
            Map<String, Object> item = new HashMap<>();
            Date date = new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000L);
            item.put("date", date);
            item.put("salesQuantity", 300 + (int)(Math.random() * 200));
            item.put("salesAmount", (300 + (int)(Math.random() * 200)) * 800);
            trend.add(item);
        }
        
        return trend;
    }
    
    @Override
    public List<Map<String, Object>> getInventoryWarning() {
        List<Map<String, Object>> warning = new ArrayList<>();
        
        Map<String, Object> item1 = new HashMap<>();
        item1.put("productName", "iPhone 12");
        item1.put("productSkuId", 5L);
        item1.put("currentStock", 8);
        item1.put("warningStock", 20);
        item1.put("shortage", 12);
        warning.add(item1);
        
        Map<String, Object> item2 = new HashMap<>();
        item2.put("productName", "华为P40");
        item2.put("productSkuId", 8L);
        item2.put("currentStock", 5);
        item2.put("warningStock", 15);
        item2.put("shortage", 10);
        warning.add(item2);
        
        return warning;
    }
    
    @Override
    public List<Map<String, Object>> getInventoryOverstock() {
        List<Map<String, Object>> overstock = new ArrayList<>();
        
        Map<String, Object> item1 = new HashMap<>();
        item1.put("productName", "红米Note 10");
        item1.put("productSkuId", 12L);
        item1.put("currentStock", 280);
        item1.put("maxStock", 100);
        item1.put("excess", 180);
        overstock.add(item1);
        
        Map<String, Object> item2 = new HashMap<>();
        item2.put("productName", "OPPO Reno5");
        item2.put("productSkuId", 15L);
        item2.put("currentStock", 220);
        item2.put("maxStock", 80);
        item2.put("excess", 140);
        overstock.add(item2);
        
        return overstock;
    }
    
    @Override
    public List<Map<String, Object>> getWarehouseInventoryStats() {
        List<Map<String, Object>> stats = new ArrayList<>();
        
        Map<String, Object> stat1 = new HashMap<>();
        stat1.put("warehouseName", "北京仓库");
        stat1.put("warehouseId", 1L);
        stat1.put("productCount", 86);
        stat1.put("totalStock", 12450);
        stat1.put("stockAmount", 8756000.00);
        stats.add(stat1);
        
        Map<String, Object> stat2 = new HashMap<>();
        stat2.put("warehouseName", "上海仓库");
        stat2.put("warehouseId", 2L);
        stat2.put("productCount", 65);
        stat2.put("totalStock", 7890);
        stat2.put("stockAmount", 5643000.00);
        stats.add(stat2);
        
        Map<String, Object> stat3 = new HashMap<>();
        stat3.put("warehouseName", "广州仓库");
        stat3.put("warehouseId", 3L);
        stat3.put("productCount", 92);
        stat3.put("totalStock", 5090);
        stat3.put("stockAmount", 3528000.00);
        stats.add(stat3);
        
        return stats;
    }
    
    @Override
    public List<Map<String, Object>> getInventoryTurnover(Date startDate, Date endDate) {
        List<Map<String, Object>> turnover = new ArrayList<>();
        
        Map<String, Object> item1 = new HashMap<>();
        item1.put("productName", "iPhone 13 Pro");
        item1.put("productSkuId", 1L);
        item1.put("turnoverRate", 2.35);
        item1.put("inventoryDays", 155);
        turnover.add(item1);
        
        Map<String, Object> item2 = new HashMap<>();
        item2.put("productName", "华为Mate 40 Pro");
        item2.put("productSkuId", 2L);
        item2.put("turnoverRate", 1.89);
        item2.put("inventoryDays", 192);
        turnover.add(item2);
        
        return turnover;
    }
    
    @Override
    public List<Map<String, Object>> getTopHotProducts(Integer limit) {
        List<Map<String, Object>> topProducts = new ArrayList<>();
        
        String[] products = {"iPhone 13 Pro", "华为Mate 40 Pro", "小米11", "红米Note 10", "OPPO Reno6"};
        Integer[] sales = {1250, 980, 850, 670, 590};
        
        for (int i = 0; i < limit && i < products.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("rank", i + 1);
            item.put("productName", products[i]);
            item.put("salesQuantity", sales[i]);
            topProducts.add(item);
        }
        
        return topProducts;
    }
}