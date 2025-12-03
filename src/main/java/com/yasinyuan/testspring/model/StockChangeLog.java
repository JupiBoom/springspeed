package com.yasinyuan.testspring.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存变更日志模型
 */
public class StockChangeLog {
    private Long id;
    private Long warehouseId;
    private Long productSkuId;
    private Integer changeType;
    private Integer changeQuantity;
    private Integer beforeStock;
    private Integer afterStock;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private String sourceType;
    private Long sourceId;
    private String orderNo;
    private String batchNo;
    private String operator;
    private Long operatorId;
    private String remarks;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
    private Boolean deleted;
    
    // 库存变更类型常量
    public static final int TYPE_IN = 1;     // 入库
    public static final int TYPE_OUT = 2;    // 出库
    public static final int TYPE_ADJUST = 3; // 调整
    public static final int TYPE_RETURN = 4; // 退货
    
    // 来源类型常量
    public static final String SOURCE_PURCHASE = "PURCHASE";          // 采购入库
    public static final String SOURCE_SALE = "SALE";                  // 销售出库
    public static final String SOURCE_RETURN = "RETURN";              // 退货入库
    public static final String SOURCE_INVENTORY = "INVENTORY";        // 盘点调整
    public static final String SOURCE_TRANSFER = "TRANSFER";          // 库存调拨
    public static final String SOURCE_MANUAL = "MANUAL";              // 手工调整
    public static final String SOURCE_REFUND = "REFUND";              // 退款出库
    
    // 构造函数
    public StockChangeLog() {}
    
    public StockChangeLog(Long warehouseId, Long productSkuId, Integer changeType, Integer changeQuantity,
                          Integer beforeStock, Integer afterStock, String sourceType, Long sourceId) {
        this.warehouseId = warehouseId;
        this.productSkuId = productSkuId;
        this.changeType = changeType;
        this.changeQuantity = changeQuantity;
        this.beforeStock = beforeStock;
        this.afterStock = afterStock;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
    }
    
    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    
    public Long getProductSkuId() { return productSkuId; }
    public void setProductSkuId(Long productSkuId) { this.productSkuId = productSkuId; }
    
    public Integer getChangeType() { return changeType; }
    public void setChangeType(Integer changeType) { this.changeType = changeType; }
    
    public Integer getChangeQuantity() { return changeQuantity; }
    public void setChangeQuantity(Integer changeQuantity) { this.changeQuantity = changeQuantity; }
    
    public Integer getBeforeStock() { return beforeStock; }
    public void setBeforeStock(Integer beforeStock) { this.beforeStock = beforeStock; }
    
    public Integer getAfterStock() { return afterStock; }
    public void setAfterStock(Integer afterStock) { this.afterStock = afterStock; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }
    
    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }
    
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    
    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }
    
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    
    public Long getOperatorId() { return operatorId; }
    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
    
    public Long getCreateBy() { return createBy; }
    public void setCreateBy(Long createBy) { this.createBy = createBy; }
    
    public Long getUpdateBy() { return updateBy; }
    public void setUpdateBy(Long updateBy) { this.updateBy = updateBy; }
    
    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
    
    @Override
    public String toString() {
        return "StockChangeLog{" +
                "id=" + id +
                ", warehouseId=" + warehouseId +
                ", productSkuId=" + productSkuId +
                ", changeType=" + changeType +
                ", changeQuantity=" + changeQuantity +
                ", beforeStock=" + beforeStock +
                ", afterStock=" + afterStock +
                ", sourceType='" + sourceType + '\'' +
                ", sourceId=" + sourceId +
                "}";
    }
}