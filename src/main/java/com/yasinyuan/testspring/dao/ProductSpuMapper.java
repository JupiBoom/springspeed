package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.ProductSpu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品SPU Mapper
 */
public interface ProductSpuMapper extends Mapper<ProductSpu> {
    
    /**
     * 根据品牌ID查询商品列表
     * @param brandId 品牌ID
     * @return 商品列表
     */
    @Select("SELECT * FROM product_spu WHERE brand_id = #{brandId} AND deleted = 0")
    List<ProductSpu> selectByBrandId(Long brandId);
    
    /**
     * 根据分类ID查询商品列表
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @Select("SELECT * FROM product_spu WHERE category_id = #{categoryId} AND deleted = 0")
    List<ProductSpu> selectByCategoryId(Long categoryId);
    
    /**
     * 根据状态查询商品列表
     * @param status 商品状态
     * @return 商品列表
     */
    @Select("SELECT * FROM product_spu WHERE status = #{status} AND deleted = 0")
    List<ProductSpu> selectByStatus(Integer status);
    
    /**
     * 更新商品状态
     * @param id 商品ID
     * @param status 商品状态
     * @return 更新结果
     */
    @Update("UPDATE product_spu SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新商品审核状态
     * @param id 商品ID
     * @param auditStatus 审核状态
     * @param auditRemark 审核备注
     * @return 更新结果
     */
    @Update("UPDATE product_spu SET audit_status = #{auditStatus}, audit_remark = #{auditRemark} WHERE id = #{id}")
    int updateAuditStatus(@Param("id") Long id, @Param("auditStatus") Integer auditStatus, @Param("auditRemark") String auditRemark);
}