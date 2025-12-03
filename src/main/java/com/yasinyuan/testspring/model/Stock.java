package com.yasinyuan.testspring.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存模型
 */
public class Stock {
    private Long id;
    private Long warehouseId;
    private Long productSkuId;
    private Integer currentStock;
    private Integer warningStock;
    private Integer allocatedStock;
    private String batchNo;
    private Date productionDate;
    private Date expireDate;
    private Integer status;
    private BigDecimal costPrice;
    private BigDecimal lastPurchasePrice;
    private Long lastInStockTime;
    private Long lastOutStockTime;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
    private Boolean deleted;
    
    // 构造函数
    public Stock() {}
    
    public Stock(Long warehouseId, Long productSkuId, Integer currentStock) {
        this.warehouseId = warehouseId;
        this.productSkuId = productSkuId;
        this.currentStock = currentStock;
    }
    
    // Getter和Setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    
    public Long getProductSkuId() { return productSkuId; }
    public void setProductSkuId(Long productSkuId) { this.productSkuId = productSkuId; }
    
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    
    public Integer getWarningStock() { return warningStock; }
    public void setWarningStock(Integer warningStock) { this.warningStock = warningStock; }
    
    public Integer getAllocatedStock() { return allocatedStock; }
    public void setAllocatedStock(Integer allocatedStock) { this.allocatedStock = allocatedStock; }
    
    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }
    
    public Date getProductionDate() { return productionDate; }
    public void setProductionDate(Date productionDate) { this.productionDate = productionDate; }
    
    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public BigDecimal getCostPrice() { return costPrice; }
    public void setCostPrice(BigDecimal costPrice) { this.costPrice = costPrice; }
    
    public BigDecimal getLastPurchasePrice() { return lastPurchasePrice; }
    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) { this.lastPurchasePrice = lastPurchasePrice; }
    
    public Long getLastInStockTime() { return lastInStockTime; }
    public void setLastInStockTime(Long lastInStockTime) { this.lastInStockTime = lastInStockTime; }
    
    public Long getLastOutStockTime() { return lastOutStockTime; }
    public void setLastOutStockTime(Long lastOutStockTime) { this.lastOutStockTime = lastOutStockTime; }
    
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
        return "Stock{" +
                "id=" + id +
                ", warehouseId=" + warehouseId +
                ", productSkuId=" + productSkuId +
                ", currentStock=" + currentStock +
                ", warningStock=" + warningStock +
                ", allocatedStock=" + allocatedStock +
                ", status=" + status +
                "}";
    }
}