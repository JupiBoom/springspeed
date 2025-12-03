package com.yasinyuan.testspring.transaction.model;

import com.yasinyuan.testspring.core.Mapper;
import com.yasinyuan.testspring.payment.enums.PaymentChannel;
import com.yasinyuan.testspring.payment.enums.PaymentStatus;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易记录实体
 * @author yinyuan
 */
@Data
@Table(name = "transaction_record")
public class TransactionRecord {
    
    /**
     * 交易流水号（主键）
     */
    @Id
    @KeySql(useGeneratedKeys = false)
    @Column(name = "transaction_no")
    private String transactionNo;
    
    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;
    
    /**
     * 支付流水号
     */
    @Column(name = "payment_no")
    private String paymentNo;
    
    /**
     * 支付渠道
     */
    @Column(name = "payment_channel")
    private PaymentChannel paymentChannel;
    
    /**
     * 支付状态
     */
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    
    /**
     * 交易金额
     */
    @Column(name = "amount")
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    @Column(name = "fee")
    private BigDecimal fee;
    
    /**
     * 实际到账金额
     */
    @Column(name = "actual_amount")
    private BigDecimal actualAmount;
    
    /**
     * 商品描述
     */
    @Column(name = "description")
    private String description;
    
    /**
     * 客户端IP
     */
    @Column(name = "client_ip")
    private String clientIp;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    
    /**
     * 支付完成时间
     */
    @Column(name = "pay_time")
    private Date payTime;
    
    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;
    
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
