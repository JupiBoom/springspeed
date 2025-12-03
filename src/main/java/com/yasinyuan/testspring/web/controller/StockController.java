package com.yasinyuan.testspring.web.controller;

import com.yasinyuan.testspring.core.Result;
import com.yasinyuan.testspring.model.Stock;
import com.yasinyuan.testspring.service.StockService;
import com.yasinyuan.testspring.tools.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理Controller
 */
@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {
    
    @Autowired
    private StockService stockService;
    
    /**
     * 获取库存列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<Stock>> list() {
        List<Stock> list = stockService.selectAll();
        return Result.success(list);
    }
    
    /**
     * 分页查询库存
     */
    @GetMapping("/page")
    @ResponseBody
    public Result<PageBean<Stock>> page(@RequestParam(defaultValue = "1") Integer page, 
                                        @RequestParam(defaultValue = "10") Integer size, 
                                        @RequestParam(required = false) Long warehouseId, 
                                        @RequestParam(required = false) Long productSkuId) {
        PageBean<Stock> pageBean = stockService.selectPage(page, size, warehouseId, productSkuId);
        return Result.success(pageBean);
    }
    
    /**
     * 按仓库和SKU查询库存
     */
    @GetMapping("/warehouse-sku")
    @ResponseBody
    public Result<Stock> getByWarehouseAndSku(@RequestParam Long warehouseId, @RequestParam Long productSkuId) {
        Stock stock = stockService.selectByWarehouseAndProductSkuId(warehouseId, productSkuId);
        return Result.success(stock);
    }
    
    /**
     * 按SKU查询所有仓库库存
     */
    @GetMapping("/sku/{skuId}")
    @ResponseBody
    public Result<List<Stock>> getBySku(@PathVariable Long skuId) {
        List<Stock> list = stockService.selectByProductSkuId(skuId);
        return Result.success(list);
    }
    
    /**
     * 获取库存预警列表
     */
    @GetMapping("/warning")
    @ResponseBody
    public Result<List<Stock>> getWarning() {
        List<Stock> list = stockService.selectWarning();
        return Result.success(list);
    }
    
    /**
     * 获取库存积压列表
     */
    @GetMapping("/overstock")
    @ResponseBody
    public Result<List<Stock>> getOverstock() {
        List<Stock> list = stockService.selectOverstock();
        return Result.success(list);
    }
    
    /**
     * 获取SKU总库存
     */
    @GetMapping("/total/sku/{skuId}")
    @ResponseBody
    public Result<Long> getTotalBySku(@PathVariable Long skuId) {
        Long total = stockService.getTotalStockByProductSkuId(skuId);
        return Result.success(total);
    }
    
    /**
     * 获取仓库总库存
     */
    @GetMapping("/total/warehouse/{warehouseId}")
    @ResponseBody
    public Result<Long> getTotalByWarehouse(@PathVariable Long warehouseId) {
        Long total = stockService.getTotalStockByWarehouseId(warehouseId);
        return Result.success(total);
    }
    
    /**
     * 库存入库
     */
    @PostMapping("/in")
    @ResponseBody
    public Result<String> stockIn(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                 @RequestParam Integer quantity, @RequestParam(required = false) String sourceType, 
                                 @RequestParam(required = false) Long sourceId, @RequestParam(required = false) String remark) {
        stockService.stockIn(warehouseId, productSkuId, quantity, sourceType, sourceId, remark);
        return Result.success("库存入库成功");
    }
    
    /**
     * 库存出库
     */
    @PostMapping("/out")
    @ResponseBody
    public Result<String> stockOut(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                  @RequestParam Integer quantity, @RequestParam(required = false) String sourceType, 
                                  @RequestParam(required = false) Long sourceId, @RequestParam(required = false) String remark) {
        stockService.stockOut(warehouseId, productSkuId, quantity, sourceType, sourceId, remark);
        return Result.success("库存出库成功");
    }
    
    /**
     * 库存调整
     */
    @PostMapping("/adjust")
    @ResponseBody
    public Result<String> stockAdjust(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                     @RequestParam Integer targetQuantity, @RequestParam String remark) {
        stockService.stockAdjust(warehouseId, productSkuId, targetQuantity, remark);
        return Result.success("库存调整成功");
    }
    
    /**
     * 检查库存是否足够
     */
    @GetMapping("/check")
    @ResponseBody
    public Result<Boolean> checkStock(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                      @RequestParam Integer quantity) {
        boolean sufficient = stockService.checkStock(warehouseId, productSkuId, quantity);
        return Result.success(sufficient);
    }
    
    /**
     * 更新预警库存
     */
    @PostMapping("/warning/update")
    @ResponseBody
    public Result<String> updateWarningStock(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                            @RequestParam Integer warningStock) {
        stockService.updateWarningStock(warehouseId, productSkuId, warningStock);
        return Result.success("预警库存更新成功");
    }
    
    /**
     * 更新最高库存
     */
    @PostMapping("/max/update")
    @ResponseBody
    public Result<String> updateMaxStock(@RequestParam Long warehouseId, @RequestParam Long productSkuId, 
                                         @RequestParam Integer maxStock) {
        stockService.updateMaxStock(warehouseId, productSkuId, maxStock);
        return Result.success("最高库存更新成功");
    }
}