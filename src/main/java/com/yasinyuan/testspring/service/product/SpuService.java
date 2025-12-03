package com.yasinyuan.testspring.service.product;

import com.yasinyuan.testspring.model.product.Spu;

import java.util.List;

public interface SpuService {
    
    /**
     * 根据条件查询SPU列表
     * @param spu 查询条件
     * @return SPU列表
     */
    List<Spu> findByCondition(Spu spu);
    
    /**
     * 根据ID查询SPU详情
     * @param id SPU ID
     * @return SPU详情
     */
    Spu findById(Long id);
    
    /**
     * 保存SPU
     * @param spu SPU信息
     * @return 保存结果
     */
    boolean save(Spu spu);
    
    /**
     * 更新SPU
     * @param spu SPU信息
     * @return 更新结果
     */
    boolean update(Spu spu);
    
    /**
     * 批量更新SPU状态
     * @param ids SPU ID列表
     * @param status 状态
     * @return 更新结果
     */
    boolean batchUpdateStatus(List<Long> ids, Integer status);
    
    /**
     * 根据ID删除SPU
     * @param id SPU ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
}
