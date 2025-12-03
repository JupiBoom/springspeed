package com.yasinyuan.testspring.settlement.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 结算记录实体类
 * 记录每笔结算的详细信息
 */
@Data
@TableName("settlement_record")
public class SettlementRecord {
    
    /**
     * 结算记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 结算流水号
     */
    private String settlementNo;
    
    /**
     * 账户所属方ID（商家ID或平台ID）
     */
    private Long ownerId;
    
    /**
     * 结算账户ID
     */
    private Long accountId;
    
    /**
     * 结算类型：MERCHANT_SETTLEMENT(商家结算), PLATFORM_SETTLEMENT(平台结算)
     */
    private String settlementType;
    
    /**
     * 结算周期（如：2024-01-01至2024-01-31）
     */
    private String settlementPeriod;
    
    /**
     * 结算总金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 手续费金额
     */
    private BigDecimal feeAmount;
    
    /**
     * 实际结算金额（totalAmount - feeAmount）
     */
    private BigDecimal actualAmount;
    
    /**
     * 结算状态：PENDING(待结算), PROCESSING(处理中), SUCCESS(成功), FAIL(失败)
     */
    private String status;
    
    /**
     * 银行流水号
     */
    private String bankFlowNo;
    
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    
    /**
     * 失败原因
     */
    private String failReason;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
}