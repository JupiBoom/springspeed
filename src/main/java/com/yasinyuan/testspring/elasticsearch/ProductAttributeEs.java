package com.yasinyuan.testspring.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 商品属性ES文档实体
 */
public class ProductAttributeEs implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Field(type = FieldType.Long)
    private Long attrId;
    
    @Field(type = FieldType.Keyword)
    private String attrName;
    
    @Field(type = FieldType.Keyword)
    private String attrValue;

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}