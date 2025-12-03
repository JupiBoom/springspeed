package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.Stock;
import com.yasinyuan.testspring.tools.PageBean;

import java.util.List;

/**
 * 库存Service接口
 */
public interface StockService extends Service<Stock> {
    
    /**
     * 根据仓库ID和SKUID查询库存
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @return 库存信息
     */
    Stock selectByWarehouseAndSku(Long warehouseId, Long productSkuId);
    
    /**
     * 根据SKU ID查询库存列表
     * @param productSkuId SKU ID
     * @return 库存列表
     */
    List<Stock> selectByProductSkuId(Long productSkuId);
    
    /**
     * 根据仓库ID查询库存列表
     * @param warehouseId 仓库ID
     * @return 库存列表
     */
    List<Stock> selectByWarehouseId(Long warehouseId);
    
    /**
     * 查询库存预警商品（库存低于预警值）
     * @return 库存列表
     */
    List<Stock> selectWarningStock();
    
    /**
     * 查询库存积压商品（库存过高）
     * @param threshold 阈值（库存数量超过此值视为积压）
     * @return 库存列表
     */
    List<Stock> selectOverstock(Integer threshold);
    
    /**
     * 查询SKU的总库存（跨仓库）
     * @param productSkuId SKU ID
     * @return 总库存
     */
    Integer selectTotalStockBySkuId(Long productSkuId);
    
    /**
     * 查询SKU的可用库存（总库存 - 已分配库存）
     * @param productSkuId SKU ID
     * @return 可用库存
     */
    Integer selectAvailableStockBySkuId(Long productSkuId);
    
    /**
     * 分页查询库存列表
     * @param page 页码
     * @param size 每页大小
     * @param warehouseId 仓库ID（可选）
     * @param productSkuId SKU ID（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    PageBean<Stock> selectPage(Integer page, Integer size, Long warehouseId, Long productSkuId, Integer status);
    
    /**
     * 更新库存数量
     * @param id 库存ID
     * @param stock 新的库存数量
     * @return 操作结果
     */
    boolean updateStock(Long id, Integer stock);
    
    /**
     * 增加库存数量
     * @param id 库存ID
     * @param quantity 增加的数量
     * @return 操作结果
     */
    boolean incrementStock(Long id, Integer quantity);
    
    /**
     * 减少库存数量
     * @param id 库存ID
     * @param quantity 减少的数量
     * @return 操作结果
     */
    boolean decrementStock(Long id, Integer quantity);
    
    /**
     * 批量更新库存
     * @param stockList 库存列表
     * @return 操作结果
     */
    boolean batchUpdateStock(List<Stock> stockList);
    
    /**
     * 入库操作
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @param quantity 入库数量
     * @param unitPrice 单价
     * @param batchNo 批次号
     * @param operator 操作人员
     * @param remarks 备注
     * @return 操作结果
     */
    boolean inStock(Long warehouseId, Long productSkuId, Integer quantity, java.math.BigDecimal unitPrice, String batchNo, String operator, String remarks);
    
    /**
     * 出库操作
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @param quantity 出库数量
     * @param unitPrice 单价
     * @param orderNo 订单号
     * @param operator 操作人员
     * @param remarks 备注
     * @return 操作结果
     */
    boolean outStock(Long warehouseId, Long productSkuId, Integer quantity, java.math.BigDecimal unitPrice, String orderNo, String operator, String remarks);
    
    /**
     * 库存调整操作
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @param quantity 调整数量（正数为增加，负数为减少）
     * @param reason 调整原因
     * @param operator 操作人员
     * @param remarks 备注
     * @return 操作结果
     */
    boolean adjustStock(Long warehouseId, Long productSkuId, Integer quantity, String reason, String operator, String remarks);
    
    /**
     * 检查库存是否足够
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @param quantity 需要的数量
     * @return 是否足够
     */
    boolean checkStock(Long warehouseId, Long productSkuId, Integer quantity);
}