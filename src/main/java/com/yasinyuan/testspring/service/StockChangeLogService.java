package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.StockChangeLog;
import com.yasinyuan.testspring.tools.PageBean;

import java.util.List;

/**
 * 库存变更日志Service接口
 */
public interface StockChangeLogService extends Service<StockChangeLog> {
    
    /**
     * 根据SKU ID查询库存变更日志
     * @param productSkuId SKU ID
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByProductSkuId(Long productSkuId, Integer page, Integer size);
    
    /**
     * 根据仓库ID查询库存变更日志
     * @param warehouseId 仓库ID
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByWarehouseId(Long warehouseId, Integer page, Integer size);
    
    /**
     * 根据变更类型查询库存变更日志
     * @param changeType 变更类型
     * @param page 页码
     * @param size 每页大小
     * @return 日志列表
     */
    List<StockChangeLog> selectByChangeType(Integer changeType, Integer page, Integer size);
    
    /**
     * 根据来源类型和来源ID查询
     * @param sourceType 来源类型
     * @param sourceId 来源ID
     * @return 日志列表
     */
    List<StockChangeLog> selectBySource(String sourceType, Long sourceId);
    
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
     * @param page 页码
     * @param size 每页大小
     * @param warehouseId 仓库ID（可选）
     * @param productSkuId SKU ID（可选）
     * @param changeType 变更类型（可选）
     * @param sourceType 来源类型（可选）
     * @param startDate 开始日期（可选）
     * @param endDate 结束日期（可选）
     * @return 分页结果
     */
    PageBean<StockChangeLog> selectPage(Integer page, Integer size, Long warehouseId, Long productSkuId, Integer changeType, String sourceType, java.util.Date startDate, java.util.Date endDate);
    
    /**
     * 批量插入库存变更日志
     * @param logList 日志列表
     * @return 操作结果
     */
    boolean batchInsert(List<StockChangeLog> logList);
    
    /**
     * 创建库存变更日志
     * @param log 库存变更日志
     * @return 操作结果
     */
    boolean createLog(StockChangeLog log);
}