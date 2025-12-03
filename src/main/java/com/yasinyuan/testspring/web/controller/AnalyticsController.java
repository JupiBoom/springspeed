package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据分析Controller
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsController extends BaseController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    /**
     * 获取商品销售统计
     */
    @GetMapping("/product-sales")
    @ResponseBody
    public Result<List<Map<String, Object>>> getProductSalesStats(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Map<String, Object>> stats = analyticsService.getProductSalesStats(startDate, endDate);
        return Result.success(stats);
    }
    
    /**
     * 获取库存统计
     */
    @GetMapping("/inventory")
    @ResponseBody
    public Result<Map<String, Object>> getInventoryStats() {
        Map<String, Object> stats = analyticsService.getInventoryStats();
        return Result.success(stats);
    }
    
    /**
     * 获取分类销售统计
     */
    @GetMapping("/category-sales")
    @ResponseBody
    public Result<List<Map<String, Object>>> getCategorySalesStats(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Map<String, Object>> stats = analyticsService.getCategorySalesStats(startDate, endDate);
        return Result.success(stats);
    }
    
    /**
     * 获取品牌销售统计
     */
    @GetMapping("/brand-sales")
    @ResponseBody
    public Result<List<Map<String, Object>>> getBrandSalesStats(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Map<String, Object>> stats = analyticsService.getBrandSalesStats(startDate, endDate);
        return Result.success(stats);
    }
    
    /**
     * 获取销售趋势
     */
    @GetMapping("/sales-trend")
    @ResponseBody
    public Result<List<Map<String, Object>>> getSalesTrend(
            @RequestParam(defaultValue = "day") String period,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Map<String, Object>> trend = analyticsService.getSalesTrend(period, startDate, endDate);
        return Result.success(trend);
    }
    
    /**
     * 获取库存预警商品
     */
    @GetMapping("/inventory-warning")
    @ResponseBody
    public Result<List<Map<String, Object>>> getInventoryWarning() {
        List<Map<String, Object>> warning = analyticsService.getInventoryWarning();
        return Result.success(warning);
    }
    
    /**
     * 获取库存积压商品
     */
    @GetMapping("/inventory-overstock")
    @ResponseBody
    public Result<List<Map<String, Object>>> getInventoryOverstock() {
        List<Map<String, Object>> overstock = analyticsService.getInventoryOverstock();
        return Result.success(overstock);
    }
    
    /**
     * 获取仓库库存统计
     */
    @GetMapping("/warehouse-inventory")
    @ResponseBody
    public Result<List<Map<String, Object>>> getWarehouseInventoryStats() {
        List<Map<String, Object>> stats = analyticsService.getWarehouseInventoryStats();
        return Result.success(stats);
    }
    
    /**
     * 获取库存周转统计
     */
    @GetMapping("/inventory-turnover")
    @ResponseBody
    public Result<List<Map<String, Object>>> getInventoryTurnover(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Map<String, Object>> turnover = analyticsService.getInventoryTurnover(startDate, endDate);
        return Result.success(turnover);
    }
    
    /**
     * 获取热门商品排行
     */
    @GetMapping("/top-products")
    @ResponseBody
    public Result<List<Map<String, Object>>> getTopHotProducts(
            @RequestParam(defaultValue = "10") Integer limit) {
        List<Map<String, Object>> topProducts = analyticsService.getTopHotProducts(limit);
        return Result.success(topProducts);
    }
    
    /**
     * 获取仪表盘数据
     */
    @GetMapping("/dashboard")
    @ResponseBody
    public Result<Map<String, Object>> getDashboardData(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Map<String, Object> dashboard = new java.util.HashMap<>();
        
        dashboard.put("inventoryStats", analyticsService.getInventoryStats());
        dashboard.put("salesTrend", analyticsService.getSalesTrend("day", startDate, endDate));
        dashboard.put("topProducts", analyticsService.getTopHotProducts(5));
        dashboard.put("categorySales", analyticsService.getCategorySalesStats(startDate, endDate));
        dashboard.put("brandSales", analyticsService.getBrandSalesStats(startDate, endDate));
        
        return Result.success(dashboard);
    }
}