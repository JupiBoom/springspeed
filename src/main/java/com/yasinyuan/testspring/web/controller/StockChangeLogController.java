package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.model.StockChangeLog;
import com.yasinyuan.testspring.service.StockChangeLogService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 库存变更日志Controller
 */
@Controller
@RequestMapping("/stock-log")
public class StockChangeLogController extends BaseController {
    
    @Autowired
    private StockChangeLogService stockChangeLogService;
    
    /**
     * 获取库存变更日志列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<StockChangeLog>> list(@RequestParam(defaultValue = "1") Integer page, 
                                             @RequestParam(defaultValue = "10") Integer size, 
                                             @RequestParam(required = false) Long warehouseId, 
                                             @RequestParam(required = false) Long productSkuId, 
                                             @RequestParam(required = false) Integer changeType, 
                                             @RequestParam(required = false) String sourceType) {
        PageBean<StockChangeLog> pageBean = stockChangeLogService.selectPage(page, size, warehouseId, productSkuId, changeType, sourceType, null, null);
        return Result.success(pageBean);
    }
    
    /**
     * 按日期范围查询
     */
    @GetMapping("/date")
    @ResponseBody
    public Result<PageBean<StockChangeLog>> byDate(@RequestParam(defaultValue = "1") Integer page, 
                                                   @RequestParam(defaultValue = "10") Integer size, 
                                                   @RequestParam Date startDate, 
                                                   @RequestParam Date endDate, 
                                                   @RequestParam(required = false) Long warehouseId, 
                                                   @RequestParam(required = false) Long productSkuId) {
        PageBean<StockChangeLog> pageBean = stockChangeLogService.selectPage(page, size, warehouseId, productSkuId, null, null, startDate, endDate);
        return Result.success(pageBean);
    }
    
    /**
     * 按SKU ID查询
     */
    @GetMapping("/sku/{skuId}")
    @ResponseBody
    public Result<List<StockChangeLog>> bySku(@PathVariable Long skuId, 
                                             @RequestParam(defaultValue = "1") Integer page, 
                                             @RequestParam(defaultValue = "10") Integer size) {
        List<StockChangeLog> list = stockChangeLogService.selectByProductSkuId(skuId, page, size);
        return Result.success(list);
    }
    
    /**
     * 按仓库ID查询
     */
    @GetMapping("/warehouse/{warehouseId}")
    @ResponseBody
    public Result<List<StockChangeLog>> byWarehouse(@PathVariable Long warehouseId, 
                                                    @RequestParam(defaultValue = "1") Integer page, 
                                                    @RequestParam(defaultValue = "10") Integer size) {
        List<StockChangeLog> list = stockChangeLogService.selectByWarehouseId(warehouseId, page, size);
        return Result.success(list);
    }
    
    /**
     * 按变更类型查询
     */
    @GetMapping("/type/{changeType}")
    @ResponseBody
    public Result<List<StockChangeLog>> byType(@PathVariable Integer changeType, 
                                               @RequestParam(defaultValue = "1") Integer page, 
                                               @RequestParam(defaultValue = "10") Integer size) {
        List<StockChangeLog> list = stockChangeLogService.selectByChangeType(changeType, page, size);
        return Result.success(list);
    }
    
    /**
     * 按来源查询
     */
    @GetMapping("/source")
    @ResponseBody
    public Result<List<StockChangeLog>> bySource(@RequestParam String sourceType, @RequestParam Long sourceId) {
        List<StockChangeLog> list = stockChangeLogService.selectBySource(sourceType, sourceId);
        return Result.success(list);
    }
    
    /**
     * 按订单号查询
     */
    @GetMapping("/order/{orderNo}")
    @ResponseBody
    public Result<List<StockChangeLog>> byOrderNo(@PathVariable String orderNo) {
        List<StockChangeLog> list = stockChangeLogService.selectByOrderNo(orderNo);
        return Result.success(list);
    }
    
    /**
     * 获取SKU变更统计
     */
    @GetMapping("/count/sku/{skuId}")
    @ResponseBody
    public Result<Long> countBySku(@PathVariable Long skuId) {
        Long count = stockChangeLogService.countByProductSkuId(skuId);
        return Result.success(count);
    }
    
    /**
     * 获取仓库变更统计
     */
    @GetMapping("/count/warehouse/{warehouseId}")
    @ResponseBody
    public Result<Long> countByWarehouse(@PathVariable Long warehouseId) {
        Long count = stockChangeLogService.countByWarehouseId(warehouseId);
        return Result.success(count);
    }
    
    /**
     * 获取日志详情
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Result<StockChangeLog> getById(@PathVariable Long id) {
        StockChangeLog log = stockChangeLogService.selectById(id);
        return Result.success(log);
    }
}