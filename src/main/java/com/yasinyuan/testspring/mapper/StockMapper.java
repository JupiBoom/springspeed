package com.yasinyuan.testspring.mapper;

import com.yasinyuan.testspring.model.Stock;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 库存Mapper
 */
public interface StockMapper extends BaseMapper<Stock> {
    
    /**
     * 根据仓库ID和SKUID查询库存
     * @param warehouseId 仓库ID
     * @param productSkuId SKU ID
     * @return 库存信息
     */
    Stock selectByWarehouseAndSku(@Param("warehouseId") Long warehouseId, @Param("productSkuId") Long productSkuId);
    
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
    List<Stock> selectOverstock(@Param("threshold") Integer threshold);
    
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
     * 更新库存数量
     * @param id 库存ID
     * @param stock 新的库存数量
     * @return 更新记录数
     */
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
    
    /**
     * 增加库存数量
     * @param id 库存ID
     * @param quantity 增加的数量
     * @return 更新记录数
     */
    int incrementStock(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    /**
     * 减少库存数量
     * @param id 库存ID
     * @param quantity 减少的数量
     * @return 更新记录数
     */
    int decrementStock(@Param("id") Long id, @Param("quantity") Integer quantity);
    
    /**
     * 批量更新库存
     * @param stockList 库存列表
     * @return 更新记录数
     */
    int batchUpdateStock(@Param("list") List<Stock> stockList);
    
    /**
     * 分页查询库存列表
     * @param params 查询参数
     * @return 库存列表
     */
    List<Stock> selectPageByParam(Map<String, Object> params);
    
    /**
     * 查询分页记录总数
     * @param params 查询参数
     * @return 记录总数
     */
    Long countByParam(Map<String, Object> params);
}