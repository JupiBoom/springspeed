package com.yasinyuan.testspring.controller;

import com.yasinyuan.testspring.model.inventory.Inventory;
import com.yasinyuan.testspring.model.inventory.InventoryLog;
import com.yasinyuan.testspring.model.inventory.InventoryWarning;
import com.yasinyuan.testspring.service.inventory.InventoryService;
import com.yasinyuan.testspring.service.inventory.InventoryLogService;
import com.yasinyuan.testspring.service.inventory.InventoryWarningService;
import com.yasinyuan.testspring.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryLogService inventoryLogService;

    @Autowired
    private InventoryWarningService inventoryWarningService;

    /**
     * 分页查询库存列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        // 注意：这里需要在InventoryService中实现queryPage方法
        // 暂时返回空列表，待实现
        return R.ok().put("page", null);
    }

    /**
     * 根据SKU ID查询库存
     */
    @GetMapping("/bySku/{skuId}")
    public R bySku(@PathVariable("skuId") Long skuId) {
        List<Inventory> inventoryList = inventoryService.findBySkuId(skuId);
        return R.ok().put("inventoryList", inventoryList);
    }

    /**
     * 根据SKU ID和仓库编码查询库存
     */
    @GetMapping("/bySkuAndWarehouse/{skuId}/{warehouseCode}")
    public R bySkuAndWarehouse(@PathVariable("skuId") Long skuId, @PathVariable("warehouseCode") String warehouseCode) {
        Inventory inventory = inventoryService.findBySkuIdAndWarehouseCode(skuId, warehouseCode);
        return R.ok().put("inventory", inventory);
    }

    /**
     * 保存库存
     */
    @PostMapping("/save")
    public R save(@RequestBody Inventory inventory) {
        inventoryService.save(inventory);
        return R.ok();
    }

    /**
     * 修改库存
     */
    @PostMapping("/update")
    public R update(@RequestBody Inventory inventory) {
        inventoryService.update(inventory);
        return R.ok();
    }

    /**
     * 删除库存
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        inventoryService.deleteBatchIds(ids);
        return R.ok();
    }

    /**
     * 扣减库存
     */
    @PostMapping("/deduct")
    public R deduct(@RequestParam Long skuId, @RequestParam String warehouseCode, @RequestParam Integer quantity) {
        boolean success = inventoryService.deductInventory(skuId, warehouseCode, quantity);
        if (success) {
            return R.ok();
        } else {
            return R.error("库存不足");
        }
    }

    /**
     * 增加库存
     */
    @PostMapping("/add")
    public R add(@RequestParam Long skuId, @RequestParam String warehouseCode, @RequestParam Integer quantity) {
        inventoryService.addInventory(skuId, warehouseCode, quantity);
        return R.ok();
    }

    /**
     * 分页查询库存日志列表
     */
    @GetMapping("/log/list")
    public R logList(@RequestParam Map<String, Object> params) {
        // 注意：这里需要在InventoryLogService中实现queryPage方法
        // 暂时返回空列表，待实现
        return R.ok().put("page", null);
    }

    /**
     * 分页查询库存预警列表
     */
    @GetMapping("/warning/list")
    public R warningList(@RequestParam Map<String, Object> params) {
        // 注意：这里需要在InventoryWarningService中实现queryPage方法
        // 暂时返回空列表，待实现
        return R.ok().put("page", null);
    }

    /**
     * 保存库存预警
     */
    @PostMapping("/warning/save")
    public R warningSave(@RequestBody InventoryWarning inventoryWarning) {
        inventoryWarningService.save(inventoryWarning);
        return R.ok();
    }

    /**
     * 修改库存预警
     */
    @PostMapping("/warning/update")
    public R warningUpdate(@RequestBody InventoryWarning inventoryWarning) {
        inventoryWarningService.update(inventoryWarning);
        return R.ok();
    }

    /**
     * 删除库存预警
     */
    @PostMapping("/warning/delete")
    public R warningDelete(@RequestBody Long[] ids) {
        inventoryWarningService.deleteBatchIds(ids);
        return R.ok();
    }
}
