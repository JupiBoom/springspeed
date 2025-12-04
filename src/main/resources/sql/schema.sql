-- 创建会员表
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `level_id` bigint(20) DEFAULT NULL COMMENT '会员等级ID',
  `growth_value` int(11) DEFAULT '0' COMMENT '成长值',
  `points` int(11) DEFAULT '0' COMMENT '积分',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1-正常，2-冻结，3-注销',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_level_id` (`level_id`),
  KEY `idx_status` (`status`),
  KEY `idx_register_time` (`register_time`),
  KEY `idx_last_login_time` (`last_login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 创建会员等级表
CREATE TABLE `member_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员等级ID',
  `name` varchar(50) NOT NULL COMMENT '会员等级名称',
  `min_growth_value` int(11) NOT NULL COMMENT '最低成长值',
  `max_growth_value` int(11) DEFAULT NULL COMMENT '最高成长值',
  `discount` decimal(4,2) DEFAULT '1.00' COMMENT '折扣',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1-启用，2-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `idx_status` (`status`),
  KEY `idx_min_growth_value` (`min_growth_value`),
  KEY `idx_max_growth_value` (`max_growth_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';

-- 创建成长值记录表
CREATE TABLE `growth_value_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成长值记录ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `growth_value` int(11) NOT NULL COMMENT '成长值',
  `business_type` tinyint(4) NOT NULL COMMENT '业务类型：1-注册，2-登录，3-购买商品，4-评价商品，5-分享商品，6-生日，99-其他',
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_business_type` (`business_type`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长值记录表';

-- 创建积分账户表
CREATE TABLE `points_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '积分账户ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `total_points` int(11) DEFAULT '0' COMMENT '总积分',
  `available_points` int(11) DEFAULT '0' COMMENT '可用积分',
  `frozen_points` int(11) DEFAULT '0' COMMENT '冻结积分',
  `expired_points` int(11) DEFAULT '0' COMMENT '过期积分',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分账户表';

-- 创建积分记录表
CREATE TABLE `points_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '积分记录ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `points` int(11) NOT NULL COMMENT '积分',
  `type` tinyint(4) NOT NULL COMMENT '类型：1-增加，2-扣除，3-冻结，4-解冻，5-过期',
  `business_type` tinyint(4) NOT NULL COMMENT '业务类型：1-注册，2-登录，3-购买商品，4-评价商品，5-分享商品，6-生日，7-积分兑换，8-积分过期，99-其他',
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_type` (`type`),
  KEY `idx_business_type` (`business_type`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_expire_time` (`expire_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- 创建会员权益记录表
CREATE TABLE `member_benefit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员权益记录ID',
  `member_id` bigint(20) NOT NULL COMMENT '会员ID',
  `benefit_type` tinyint(4) NOT NULL COMMENT '权益类型：1-运费券，2-生日积分，3-专属客服，4-会员折扣，5-免费配送，6-专属礼品，99-其他',
  `benefit_value` varchar(100) DEFAULT NULL COMMENT '权益值',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1-未使用，2-已使用，3-已过期',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `issue_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发放时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `reason` varchar(200) DEFAULT NULL COMMENT '发放原因',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_benefit_type` (`benefit_type`),
  KEY `idx_status` (`status`),
  KEY `idx_expire_time` (`expire_time`),
  KEY `idx_issue_time` (`issue_time`),
  KEY `idx_use_time` (`use_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员权益记录表';

-- 创建积分获取规则表
CREATE TABLE `points_gain_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '积分获取规则ID',
  `business_type` tinyint(4) NOT NULL COMMENT '业务类型：1-注册，2-登录，3-购买商品，4-评价商品，5-分享商品，6-生日，99-其他',
  `points` int(11) NOT NULL COMMENT '积分',
  `daily_limit` int(11) DEFAULT NULL COMMENT '每日限制',
  `monthly_limit` int(11) DEFAULT NULL COMMENT '每月限制',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态：1-启用，2-禁用',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_business_type` (`business_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分获取规则表';

-- 插入初始会员等级数据
INSERT INTO `member_level` (`name`, `min_growth_value`, `max_growth_value`, `discount`, `description`) VALUES
('普通会员', 0, 999, 1.00, '普通会员等级'),
('银卡会员', 1000, 4999, 0.95, '银卡会员等级'),
('金卡会员', 5000, 9999, 0.90, '金卡会员等级'),
('白金会员', 10000, 19999, 0.85, '白金会员等级'),
('钻石会员', 20000, NULL, 0.80, '钻石会员等级');

-- 插入初始积分获取规则数据
INSERT INTO `points_gain_rule` (`business_type`, `points`, `daily_limit`, `monthly_limit`, `description`) VALUES
(1, 100, NULL, NULL, '注册赠送积分'),
(2, 10, 50, 1000, '每日登录赠送积分'),
(3, 1, NULL, NULL, '购买商品赠送积分（1元=1积分）'),
(4, 20, 100, 2000, '评价商品赠送积分'),
(5, 30, 150, 3000, '分享商品赠送积分'),
(6, 500, NULL, NULL, '生日赠送积分');