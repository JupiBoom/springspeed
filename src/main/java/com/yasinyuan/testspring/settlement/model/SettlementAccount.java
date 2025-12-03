package com.yasinyuan.testspring.settlement.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 结算账户实体类
 * 记录商家或平台的结算账户信息
 */
@Data
@TableName("settlement_account")
public class SettlementAccount {
    
    /**
     * 结算账户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 账户所属方ID（商家ID或平台ID）
     */
    private Long ownerId;
    
    /**
     * 账户类型：MERCHANT(商家结算账户), PLATFORM(平台结算账户)
     */
    private String accountType;
    
    /**
     * 结算账户名称
     */
    private String accountName;
    
    /**
     * 银行名称
     */
    private String bankName;
    
    /**
     * 银行账号
     */
    private String bankAccount;
    
    /**
     * 开户人姓名
     */
    private String accountHolder;
    
    /**
     * 开户行联行号
     */
    private String bankCode;
    
    /**
     * 账户状态：ACTIVE(激活), INACTIVE(未激活), LOCKED(锁定)
     */
    private String status;
    
    /**
     * 结算周期：DAILY(日结), WEEKLY(周结), MONTHLY(月结)
     */
    private String settlementCycle;
    
    /**
     * 结算阈值（达到该金额才会结算）
     */
    private BigDecimal settlementThreshold;
    
    /**
     * 累计待结算金额
     */
    private BigDecimal pendingAmount;
    
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