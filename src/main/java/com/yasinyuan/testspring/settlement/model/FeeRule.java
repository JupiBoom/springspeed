package com.yasinyuan.testspring.settlement.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 手续费计算规则实体类
 * 定义不同支付渠道和交易类型的手续费计算规则
 */
@Data
@TableName("fee_rule")
public class FeeRule {
    
    /**
     * 规则ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 支付渠道：WECHAT_PAY(微信支付), ALIPAY(支付宝), UNIONPAY(银联支付)
     */
    private String paymentChannel;
    
    /**
     * 交易类型：PAYMENT(支付), REFUND(退款), TRANSFER(转账)
     */
    private String tradeType;
    
    /**
     * 规则类型：FIXED(固定金额), PERCENTAGE(百分比), HYBRID(混合模式)
     */
    private String ruleType;
    
    /**
     * 固定手续费（当ruleType为FIXED或HYBRID时有效）
     */
    private BigDecimal fixedFee;
    
    /**
     * 手续费比例（当ruleType为PERCENTAGE或HYBRID时有效，单位：%）
     */
    private BigDecimal percentage;
    
    /**
     * 最低手续费
     */
    private BigDecimal minFee;
    
    /**
     * 最高手续费
     */
    private BigDecimal maxFee;
    
    /**
     * 规则状态：ACTIVE(激活), INACTIVE(未激活)
     */
    private String status;
    
    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;
    
    /**
     * 失效时间
     */
    private LocalDateTime expireTime;
    
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