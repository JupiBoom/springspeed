package com.yasinyuan.testspring.mapper;

import com.yasinyuan.testspring.model.StockChangeLog;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 库存变更日志Mapper
 */
public interface StockChangeLogMapper extends BaseMapper<StockChangeLog> {
    
    /**
     * 根据SKU ID查询库存变更日志
     * @param productSkuId SKU ID
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByProductSkuId(@Param("productSkuId") Long productSkuId, 
                                               @Param("page") Integer page, 
                                               @Param("size") Integer size);
    
    /**
     * 根据仓库ID查询库存变更日志
     * @param warehouseId 仓库ID
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByWarehouseId(@Param("warehouseId") Long warehouseId, 
                                              @Param("page") Integer page, 
                                              @Param("size") Integer size);
    
    /**
     * 根据变更类型查询库存变更日志
     * @param changeType 变更类型
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByChangeType(@Param("changeType") Integer changeType, 
                                             @Param("page") Integer page, 
                                             @Param("size") Integer size);
    
    /**
     * 根据来源类型和来源ID查询
     * @param sourceType 来源类型
     * @param sourceId 来源ID
     * @return 日志列表
     */
    List<StockChangeLog> selectBySource(@Param("sourceType") String sourceType, 
                                         @Param("sourceId") Long sourceId);
    
    /**
     * 根据订单号查询
     * @param orderNo 订单号
     * @return 日志列表
     */
    List<StockChangeLog> selectByOrderNo(String orderNo);
    
    /**
     * 查询SKU的库存变更总数
     * @param productSkuId SKU ID
     * @return 记录总数
     */
    Long countByProductSkuId(Long productSkuId);
    
    /**
     * 查询仓库的库存变更总数
     * @param warehouseId 仓库ID
     * @return 记录总数
     */
    Long countByWarehouseId(Long warehouseId);
    
    /**
     * 分页查询库存变更日志
     * @param params 查询参数
     * @return 日志列表
     */
    List<StockChangeLog> selectPageByParam(Map<String, Object> params);
    
    /**
     * 查询分页记录总数
     * @param params 查询参数
     * @return 记录总数
     */
    Long countByParam(Map<String, Object> params);
    
    /**
     * 批量插入库存变更日志
     * @param logList 日志列表
     * @return 插入记录数
     */
    int batchInsert(@Param("list") List<StockChangeLog> logList);
}