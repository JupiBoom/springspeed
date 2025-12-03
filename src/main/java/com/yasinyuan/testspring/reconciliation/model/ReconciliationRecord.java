package com.yasinyuan.testspring.reconciliation.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 对账记录实体类
 * 记录每笔对账的详细信息
 */
@Data
@TableName("reconciliation_record")
public class ReconciliationRecord {
    
    /**
     * 对账记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 对账流水号
     */
    private String reconciliationNo;
    
    /**
     * 对账日期（格式：YYYY-MM-DD）
     */
    private String reconciliationDate;
    
    /**
     * 对账类型：PAYMENT(支付对账), SETTLEMENT(结算对账), REFUND(退款对账)
     */
    private String reconciliationType;
    
    /**
     * 支付渠道：WECHAT_PAY(微信支付), ALIPAY(支付宝), UNIONPAY(银联支付), ALL(全部)
     */
    private String paymentChannel;
    
    /**
     * 系统交易笔数
     */
    private Integer systemTradeCount;
    
    /**
     * 系统交易总金额
     */
    private BigDecimal systemTradeAmount;
    
    /**
     * 渠道交易笔数
     */
    private Integer channelTradeCount;
    
    /**
     * 渠道交易总金额
     */
    private BigDecimal channelTradeAmount;
    
    /**
     * 对账结果：MATCH(一致), MISMATCH(不一致), PARTIAL_MATCH(部分一致)
     */
    private String reconciliationResult;
    
    /**
     * 差异笔数
     */
    private Integer discrepancyCount;
    
    /**
     * 差异金额
     */
    private BigDecimal discrepancyAmount;
    
    /**
     * 对账状态：PENDING(待对账), PROCESSING(处理中), COMPLETED(已完成), FAILED(失败)
     */
    private String status;
    
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
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