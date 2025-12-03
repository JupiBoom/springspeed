package com.yasinyuan.testspring.settlement.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户账户实体类
 * 记录用户的资金账户信息
 */
@Data
@TableName("user_account")
public class UserAccount {
    
    /**
     * 账户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 账户类型：BALANCE(余额账户), FROZEN(冻结账户), INCOME(收入账户)
     */
    private String accountType;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    
    /**
     * 可用余额（balance - frozenAmount）
     */
    private BigDecimal availableBalance;
    
    /**
     * 账户状态：ACTIVE(激活), INACTIVE(未激活), LOCKED(锁定)
     */
    private String status;
    
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