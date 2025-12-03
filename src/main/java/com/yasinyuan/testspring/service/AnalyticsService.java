package com.yasinyuan.testspring.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据分析Service接口
 */
public interface AnalyticsService {
    
    /**
     * 获取商品销售统计
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 销售统计数据
     */
    List<Map<String, Object>> getProductSalesStats(Date startDate, Date endDate);
    
    /**
     * 获取库存统计
     * @return 库存统计数据
     */
    Map<String, Object> getInventoryStats();
    
    /**
     * 获取商品分类销售统计
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分类销售统计
     */
    List<Map<String, Object>> getCategorySalesStats(Date startDate, Date endDate);
    
    /**
     * 获取商品品牌销售统计
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 品牌销售统计
     */
    List<Map<String, Object>> getBrandSalesStats(Date startDate, Date endDate);
    
    /**
     * 获取销售趋势统计
     * @param period 时间周期（day/week/month/year）
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 销售趋势数据
     */
    List<Map<String, Object>> getSalesTrend(String period, Date startDate, Date endDate);
    
    /**
     * 获取库存预警商品列表
     * @return 预警商品列表
     */
    List<Map<String, Object>> getInventoryWarning();
    
    /**
     * 获取库存积压商品列表
     * @return 积压商品列表
     */
    List<Map<String, Object>> getInventoryOverstock();
    
    /**
     * 获取仓库库存统计
     * @return 仓库库存统计
     */
    List<Map<String, Object>> getWarehouseInventoryStats();
    
    /**
     * 获取库存周转统计
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 库存周转统计
     */
    List<Map<String, Object>> getInventoryTurnover(Date startDate, Date endDate);
    
    /**
     * 获取热门商品排行
     * @param limit 返回数量
     * @return 热门商品排行
     */
    List<Map<String, Object>> getTopHotProducts(Integer limit);
}