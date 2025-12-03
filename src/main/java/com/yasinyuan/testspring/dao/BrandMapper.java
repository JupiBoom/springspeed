package com.yasinyuan.testspring.dao;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.model.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 品牌 Mapper
 */
public interface BrandMapper extends Mapper<Brand> {
    
    /**
     * 查询启用的品牌列表
     * @return 品牌列表
     */
    @Select("SELECT * FROM brand WHERE status = 1 AND deleted = 0 ORDER BY sort ASC")
    List<Brand> selectEnableBrands();
}