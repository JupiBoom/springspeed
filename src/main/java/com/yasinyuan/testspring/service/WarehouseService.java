package com.yasinyuan.testspring.service;

import com.yasinyuan.testspring.model.Warehouse;
import com.yasinyuan.testspring.tools.PageBean;

import java.util.List;

/**
 * 仓库Service接口
 */
public interface WarehouseService extends Service<Warehouse> {
    
    /**
     * 根据仓库编码查询
     * @param code 仓库编码
     * @return 仓库信息
     */
    Warehouse selectByCode(String code);
    
    /**
     * 根据仓库名称模糊查询
     * @param name 仓库名称
     * @return 仓库列表
     */
    List<Warehouse> selectByNameLike(String name);
    
    /**
     * 查询启用的仓库列表
     * @return 仓库列表
     */
    List<Warehouse> selectEnabled();
    
    /**
     * 分页查询仓库列表
     * @param page 页码
     * @param size 每页大小
     * @param name 仓库名称（可选）
     * @param status 状态（可选）
     * @return 分页结果
     */
    PageBean<Warehouse> selectPage(Integer page, Integer size, String name, Integer status);
    
    /**
     * 检查仓库编码是否存在
     * @param code 仓库编码
     * @param excludeId 排除的ID（用于更新时检查）
     * @return 是否存在
     */
    boolean existsByCode(String code, Long excludeId);
    
    /**
     * 启用仓库
     * @param id 仓库ID
     * @return 操作结果
     */
    boolean enable(Long id);
    
    /**
     * 禁用仓库
     * @param id 仓库ID
     * @return 操作结果
     */
    boolean disable(Long id);
}