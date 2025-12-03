package com.yasinyuan.testspring.dao.product;

import com.yasinyuan.testspring.model.product.Spu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {
    
    /**
     * 根据条件查询SPU列表
     * @param spu 查询条件
     * @return SPU列表
     */
    List<Spu> selectByCondition(Spu spu);
    
    /**
     * 根据ID查询SPU详情
     * @param id SPU ID
     * @return SPU详情
     */
    Spu selectById(Long id);
    
    /**
     * 批量更新SPU状态
     * @param ids SPU ID列表
     * @param status 状态
     * @return 影响行数
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
