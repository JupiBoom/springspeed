package com.yasinyuan.testspring.reconciliation.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 对账差异记录实体类
 * 记录对账过程中发现的具体差异信息
 */
@Data
@TableName("reconciliation_discrepancy")
public class ReconciliationDiscrepancy {
    
    /**
     * 差异记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 对账流水号
     */
    private String reconciliationNo;
    
    /**
     * 系统交易流水号
     */
    private String systemTradeNo;
    
    /**
     * 渠道交易流水号
     */
    private String channelTradeNo;
    
    /**
     * 交易类型：PAYMENT(支付), REFUND(退款), SETTLEMENT(结算)
     */
    private String tradeType;
    
    /**
     * 系统交易金额
     */
    private BigDecimal systemAmount;
    
    /**
     * 渠道交易金额
     */
    private BigDecimal channelAmount;
    
    /**
     * 差异类型：AMOUNT_MISMATCH(金额不一致), MISSING_IN_SYSTEM(系统缺失), MISSING_IN_CHANNEL(渠道缺失), DUPLICATE(重复交易)
     */
    private String discrepancyType;
    
    /**
     * 差异金额（channelAmount - systemAmount）
     */
    private BigDecimal discrepancyAmount;
    
    /**
     * 处理状态：PENDING(待处理), PROCESSING(处理中), RESOLVED(已解决), UNRESOLVED(未解决)
     */
    private String status;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 处理备注
     */
    private String handleRemark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}