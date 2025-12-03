package com.yasinyuan.testspring.controller;

import com.yasinyuan.testspring.service.analysis.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class DataAnalysisController {
    
    @Autowired
    private DataAnalysisService dataAnalysisService;
    
    /**
     * 获取销售排行数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param topN 前N名
     * @return 销售排行数据
     */
    @GetMapping("/sales-ranking")
    public List<Map<String, Object>> getSalesRanking(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "topN", defaultValue = "10") Integer topN) {
        
        return dataAnalysisService.getSalesRanking(startDate, endDate, topN);
    }
    
    /**
     * 获取库存周转率数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 库存周转率数据
     */
    @GetMapping("/inventory-turnover")
    public List<Map<String, Object>> getInventoryTurnover(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        return dataAnalysisService.getInventoryTurnover(startDate, endDate);
    }
    
    /**
     * 获取商品上下架效率数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 上下架效率数据
     */
    @GetMapping("/listing-efficiency")
    public List<Map<String, Object>> getProductListingEfficiency(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        return dataAnalysisService.getProductListingEfficiency(startDate, endDate);
    }
    
    /**
     * 获取商品销售趋势数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param period 时间周期：天、周、月
     * @return 销售趋势数据
     */
    @GetMapping("/sales-trend")
    public List<Map<String, Object>> getSalesTrend(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "period", defaultValue = "day") String period) {
        
        return dataAnalysisService.getSalesTrend(startDate, endDate, period);
    }
    
    /**
     * 获取库存预警统计数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 库存预警统计数据
     */
    @GetMapping("/inventory-warning-stats")
    public List<Map<String, Object>> getInventoryWarningStats(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        
        return dataAnalysisService.getInventoryWarningStats(startDate, endDate);
    }
}
