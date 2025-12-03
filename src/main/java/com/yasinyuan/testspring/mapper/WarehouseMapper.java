package com.yasinyuan.testspring.mapper;

import com.yasinyuan.testspring.model.Warehouse;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 仓库Mapper
 */
public interface WarehouseMapper extends BaseMapper<Warehouse> {
    
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
    List<Warehouse> selectByNameLike(@Param("name") String name);
    
    /**
     * 查询启用的仓库列表
     * @return 仓库列表
     */
    List<Warehouse> selectEnabled();
    
    /**
     * 分页查询仓库列表
     * @param params 查询参数
     * @return 仓库列表
     */
    List<Warehouse> selectPageByParam(Map<String, Object> params);
    
    /**
     * 查询分页记录总数
     * @param params 查询参数
     * @return 记录总数
     */
    Long countByParam(Map<String, Object> params);
}