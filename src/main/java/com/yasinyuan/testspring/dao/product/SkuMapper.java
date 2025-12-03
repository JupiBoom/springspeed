package com.yasinyuan.testspring.dao.product;

import com.yasinyuan.testspring.model.product.Sku;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuMapper extends Mapper<Sku> {
    
    /**
     * 根据SPU ID查询SKU列表
     * @param spuId SPU ID
     * @return SKU列表
     */
    List<Sku> selectBySpuId(Long spuId);
    
    /**
     * 根据ID查询SKU详情
     * @param id SKU ID
     * @return SKU详情
     */
    Sku selectById(Long id);
    
    /**
     * 批量更新SKU库存
     * @param skus SKU列表
     * @return 影响行数
     */
    int batchUpdateStock(@Param("skus") List<Sku> skus);
    
    /**
     * 批量更新SKU状态
     * @param ids SKU ID列表
     * @param status 状态
     * @return 影响行数
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
