-- 创建支付交易表
CREATE TABLE IF NOT EXISTS payment_transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    payment_no VARCHAR(64) NOT NULL UNIQUE COMMENT '支付流水号',
    order_no VARCHAR(64) NOT NULL COMMENT '订单号',
    amount DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    channel VARCHAR(32) NOT NULL COMMENT '支付渠道（ALIPAY, WECHAT, UNIONPAY）',
    status VARCHAR(32) NOT NULL COMMENT '支付状态（PENDING, SUCCESS, FAILED, CLOSED）',
    channel_transaction_no VARCHAR(64) COMMENT '渠道交易号',
    description VARCHAR(255) COMMENT '商品描述',
    attach VARCHAR(500) COMMENT '附加信息',
    timeout_time DATETIME NOT NULL COMMENT '超时时间',
    pay_time DATETIME COMMENT '支付时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_no (order_no),
    INDEX idx_channel (channel),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) COMMENT '支付交易表';

-- 创建商户账户表
CREATE TABLE IF NOT EXISTS merchant_account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    merchant_id BIGINT NOT NULL UNIQUE COMMENT '商户ID',
    available_balance DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '可用余额',
    frozen_balance DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '冻结余额',
    total_income DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '累计收入',
    total_expense DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '累计支出',
    settlement_cycle VARCHAR(32) NOT NULL DEFAULT 'T+1' COMMENT '结算周期（T+1, T+3, T+7）',
    fee_rate DECIMAL(4,2) NOT NULL DEFAULT 0.60 COMMENT '手续费率（%）',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0:禁用, 1:启用）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_merchant_id (merchant_id),
    INDEX idx_status (status)
) COMMENT '商户账户表';

-- 创建结算记录表
CREATE TABLE IF NOT EXISTS settlement_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    settlement_no VARCHAR(64) NOT NULL UNIQUE COMMENT '结算单号',
    merchant_id BIGINT NOT NULL COMMENT '商户ID',
    settlement_date DATE NOT NULL COMMENT '结算日期',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '交易总金额',
    fee_amount DECIMAL(10,2) NOT NULL COMMENT '手续费',
    actual_amount DECIMAL(10,2) NOT NULL COMMENT '实际结算金额',
    transaction_count INT NOT NULL COMMENT '交易笔数',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '结算状态（0:待结算, 1:结算中, 2:结算成功, 3:结算失败）',
    settlement_time DATETIME COMMENT '结算时间',
    bank_transaction_no VARCHAR(64) COMMENT '银行流水号',
    fail_reason VARCHAR(255) COMMENT '失败原因',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_merchant_id (merchant_id),
    INDEX idx_settlement_date (settlement_date),
    INDEX idx_status (status),
    FOREIGN KEY (merchant_id) REFERENCES merchant_account(merchant_id)
) COMMENT '结算记录表';

-- 创建对账记录表
CREATE TABLE IF NOT EXISTS reconciliation_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    reconciliation_date DATE NOT NULL COMMENT '对账日期',
    channel VARCHAR(32) NOT NULL COMMENT '支付渠道（ALIPAY, WECHAT, UNIONPAY）',
    channel_total_count INT NOT NULL COMMENT '渠道交易总笔数',
    channel_total_amount DECIMAL(10,2) NOT NULL COMMENT '渠道交易总金额',
    system_total_count INT NOT NULL COMMENT '系统交易总笔数',
    system_total_amount DECIMAL(10,2) NOT NULL COMMENT '系统交易总金额',
    diff_count INT NOT NULL COMMENT '差异笔数',
    diff_amount DECIMAL(10,2) NOT NULL COMMENT '差异金额',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '对账状态（0:待对账, 1:对账中, 2:对账成功, 3:对账失败）',
    result_desc VARCHAR(255) COMMENT '对账结果描述',
    bill_file_path VARCHAR(500) COMMENT '对账单文件路径',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_reconciliation_date_channel (reconciliation_date, channel),
    INDEX idx_channel (channel),
    INDEX idx_status (status)
) COMMENT '对账记录表';

-- 插入测试数据
INSERT INTO merchant_account (merchant_id, available_balance, frozen_balance, total_income, total_expense, settlement_cycle, fee_rate, status) 
VALUES (1, 10000.00, 0.00, 0.00, 0.00, 'T+1', 0.60, 1) 
ON DUPLICATE KEY UPDATE available_balance = available_balance;
