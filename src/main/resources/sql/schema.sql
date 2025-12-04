-- 会员体系表
CREATE TABLE `t_member` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
    `member_no` varchar(32) NOT NULL COMMENT '会员编号',
    `username` varchar(64) DEFAULT NULL COMMENT '用户名',
    `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
    `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
    `level_code` int(11) DEFAULT 1 COMMENT '等级代码',
    `growth_value` int(11) DEFAULT 0 COMMENT '成长值',
    `valid_start_time` datetime DEFAULT NULL COMMENT '会员有效期开始时间',
    `valid_end_time` datetime DEFAULT NULL COMMENT '会员有效期结束时间',
    `birthday` date DEFAULT NULL COMMENT '生日',
    `customer_service_id` bigint(20) DEFAULT NULL COMMENT '专属客服ID',
    `status` int(11) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` int(11) DEFAULT 0 COMMENT '逻辑删除标识',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_member_no` (`member_no`),
    KEY `idx_level_code` (`level_code`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

CREATE TABLE `t_member_level_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `level_code` int(11) NOT NULL COMMENT '等级代码',
    `level_name` varchar(32) NOT NULL COMMENT '等级名称',
    `required_growth_value` int(11) NOT NULL COMMENT '所需成长值',
    `valid_days` int(11) DEFAULT 365 COMMENT '有效期天数',
    `discount_rate` int(11) DEFAULT 100 COMMENT '折扣比例（0-100）',
    `monthly_freight_coupon_count` int(11) DEFAULT 0 COMMENT '每月运费券数量',
    `priority_shipping` tinyint(1) DEFAULT 0 COMMENT '是否优先发货',
    `birthday_point_multiple` int(11) DEFAULT 1 COMMENT '生日积分倍数',
    `customer_service_level` varchar(32) DEFAULT NULL COMMENT '客服等级',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_level_code` (`level_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级配置表';

CREATE TABLE `t_member_growth_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `change_type` int(11) NOT NULL COMMENT '变动类型：1-消费获得，2-签到获得，3-活动奖励，4-等级扣除，5-过期扣除',
    `change_value` int(11) NOT NULL COMMENT '变动值（正数为增加，负数为减少）',
    `before_value` int(11) NOT NULL COMMENT '变动前成长值',
    `after_value` int(11) NOT NULL COMMENT '变动后成长值',
    `business_id` varchar(64) DEFAULT NULL COMMENT '关联业务ID',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`),
    KEY `idx_change_type` (`change_type`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员成长值记录表';

-- 积分系统表
CREATE TABLE `t_point_account` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `total_points` int(11) DEFAULT 0 COMMENT '总积分',
    `available_points` int(11) DEFAULT 0 COMMENT '可用积分',
    `frozen_points` int(11) DEFAULT 0 COMMENT '冻结积分',
    `expired_points` int(11) DEFAULT 0 COMMENT '已过期积分',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分账户表';

CREATE TABLE `t_point_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `change_type` int(11) NOT NULL COMMENT '变动类型',
    `change_value` int(11) NOT NULL COMMENT '变动值（正数为增加，负数为减少）',
    `before_total` int(11) NOT NULL COMMENT '变动前总积分',
    `after_total` int(11) NOT NULL COMMENT '变动后总积分',
    `before_available` int(11) NOT NULL COMMENT '变动前可用积分',
    `after_available` int(11) NOT NULL COMMENT '变动后可用积分',
    `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
    `business_id` varchar(64) DEFAULT NULL COMMENT '关联业务ID',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`),
    KEY `idx_change_type` (`change_type`),
    KEY `idx_expire_time` (`expire_time`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分变动记录表';

CREATE TABLE `t_point_frozen_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `frozen_value` int(11) NOT NULL COMMENT '冻结积分值',
    `remaining_value` int(11) NOT NULL COMMENT '剩余冻结值',
    `reason` varchar(255) DEFAULT NULL COMMENT '冻结原因',
    `freeze_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '冻结开始时间',
    `unfreeze_time` datetime DEFAULT NULL COMMENT '冻结结束时间',
    `status` int(11) DEFAULT 0 COMMENT '状态：0-冻结中，1-已解冻，2-已过期',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`),
    KEY `idx_status` (`status`),
    KEY `idx_unfreeze_time` (`unfreeze_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分冻结记录表';

CREATE TABLE `t_point_rule_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `rule_type` int(11) NOT NULL COMMENT '规则类型：1-消费积分，2-签到积分，3-活动积分，4-积分抵扣',
    `rule_name` varchar(64) NOT NULL COMMENT '规则名称',
    `point_ratio` int(11) DEFAULT 1 COMMENT '积分比例',
    `daily_limit` int(11) DEFAULT 0 COMMENT '每日上限',
    `monthly_limit` int(11) DEFAULT 0 COMMENT '每月上限',
    `expire_days` int(11) DEFAULT 365 COMMENT '积分有效期天数',
    `deduct_ratio` int(11) DEFAULT 100 COMMENT '抵扣比例（0-100）',
    `sign_in_base_points` int(11) DEFAULT 10 COMMENT '每日签到基础积分',
    `continuous_sign_in_extra_points` int(11) DEFAULT 5 COMMENT '连续签到额外积分',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `status` int(11) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_rule_type` (`rule_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分规则配置表';

-- 会员权益表
CREATE TABLE `t_member_benefit` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权益ID',
    `benefit_name` varchar(64) NOT NULL COMMENT '权益名称',
    `benefit_type` int(11) NOT NULL COMMENT '权益类型：1-折扣优惠，2-运费券，3-优先发货，4-生日特权，5-专属客服，6-专属活动',
    `applicable_levels` varchar(32) DEFAULT '1,2,3,4' COMMENT '适用等级代码',
    `benefit_value` int(11) DEFAULT 0 COMMENT '权益值',
    `issue_cycle` int(11) DEFAULT 1 COMMENT '发放周期：1-一次性，2-每日，3-每月，4-每年',
    `monthly_issue_count` int(11) DEFAULT 1 COMMENT '每月发放数量',
    `limit_count_per_person` int(11) DEFAULT 0 COMMENT '每人限领数量',
    `valid_days` int(11) DEFAULT 30 COMMENT '有效期天数',
    `anti_brush_config` varchar(255) DEFAULT NULL COMMENT '防刷配置',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `status` int(11) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_benefit_type` (`benefit_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员权益表';

CREATE TABLE `t_benefit_issue_record` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `benefit_id` bigint(20) NOT NULL COMMENT '权益ID',
    `benefit_name` varchar(64) NOT NULL COMMENT '权益名称',
    `benefit_type` int(11) NOT NULL COMMENT '权益类型',
    `benefit_value` int(11) DEFAULT 0 COMMENT '权益值',
    `issue_count` int(11) DEFAULT 1 COMMENT '发放数量',
    `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` datetime DEFAULT NULL COMMENT '结束时间',
    `use_status` int(11) DEFAULT 0 COMMENT '使用状态：0-未使用，1-已使用，2-已过期',
    `use_time` datetime DEFAULT NULL COMMENT '使用时间',
    `anti_brush_check_result` varchar(255) DEFAULT NULL COMMENT '防刷检查结果',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`),
    KEY `idx_benefit_id` (`benefit_id`),
    KEY `idx_use_status` (`use_status`),
    KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权益发放记录表';

CREATE TABLE `t_birthday_privilege_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `level_code` int(11) NOT NULL COMMENT '等级代码',
    `point_reward` int(11) DEFAULT 0 COMMENT '生日积分奖励',
    `birthday_discount` int(11) DEFAULT 100 COMMENT '生日折扣（0-100）',
    `coupon_amount` int(11) DEFAULT 0 COMMENT '生日优惠券金额',
    `issue_days_in_advance` int(11) DEFAULT 3 COMMENT '提前几天发放',
    `valid_days` int(11) DEFAULT 7 COMMENT '特权有效期天数',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `status` int(11) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_level_code` (`level_code`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生日特权配置表';

CREATE TABLE `t_customer_service_allocation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分配ID',
    `service_id` bigint(20) NOT NULL COMMENT '客服ID',
    `service_name` varchar(32) NOT NULL COMMENT '客服姓名',
    `service_level` varchar(32) DEFAULT '普通' COMMENT '客服等级',
    `responsible_levels` varchar(32) DEFAULT '1,2,3,4' COMMENT '负责等级代码',
    `allocated_member_count` int(11) DEFAULT 0 COMMENT '已分配会员数',
    `max_member_count` int(11) DEFAULT 1000 COMMENT '最大承载会员数',
    `online_status` int(11) DEFAULT 1 COMMENT '在线状态：0-离线，1-在线',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `status` int(11) DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_service_id` (`service_id`),
    KEY `idx_service_level` (`service_level`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客服分配表';

-- 数据分析表
CREATE TABLE `t_member_lifecycle_value` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分析ID',
    `member_id` bigint(20) NOT NULL COMMENT '会员ID',
    `first_purchase_time` datetime DEFAULT NULL COMMENT '首次消费时间',
    `last_purchase_time` datetime DEFAULT NULL COMMENT '末次消费时间',
    `total_purchase_count` int(11) DEFAULT 0 COMMENT '累计消费次数',
    `total_purchase_amount` decimal(18,2) DEFAULT 0.00 COMMENT '累计消费金额',
    `avg_order_amount` decimal(18,2) DEFAULT 0.00 COMMENT '平均客单价',
    `total_earned_points` int(11) DEFAULT 0 COMMENT '累计获取积分',
    `total_used_points` int(11) DEFAULT 0 COMMENT '累计使用积分',
    `lifecycle_days` int(11) DEFAULT 0 COMMENT '会员生命周期天数',
    `r_value` int(11) DEFAULT 0 COMMENT 'R值',
    `f_value` int(11) DEFAULT 0 COMMENT 'F值',
    `m_value` decimal(18,2) DEFAULT 0.00 COMMENT 'M值',
    `value_level` int(11) DEFAULT 1 COMMENT '会员价值等级',
    `analysis_cycle` int(11) DEFAULT 3 COMMENT '分析周期：1-日，2-周，3-月，4-季度，5-年',
    `analysis_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '分析日期',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_member_id` (`member_id`),
    KEY `idx_value_level` (`value_level`),
    KEY `idx_analysis_date` (`analysis_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员生命周期价值分析表';

CREATE TABLE `t_point_usage_efficiency` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分析ID',
    `statistics_cycle` int(11) NOT NULL COMMENT '统计周期：1-日，2-周，3-月，4-季度，5-年',
    `start_time` datetime NOT NULL COMMENT '统计开始时间',
    `end_time` datetime NOT NULL COMMENT '统计结束时间',
    `total_earned_points` int(11) DEFAULT 0 COMMENT '新增积分总额',
    `total_used_points` int(11) DEFAULT 0 COMMENT '使用积分总额',
    `total_expired_points` int(11) DEFAULT 0 COMMENT '过期积分总额',
    `usage_rate` decimal(5,2) DEFAULT 0.00 COMMENT '积分使用率',
    `expire_rate` decimal(5,2) DEFAULT 0.00 COMMENT '积分过期率',
    `avg_retention_days` int(11) DEFAULT 0 COMMENT '平均积分留存天数',
    `consumption_scenario_distribution` varchar(500) DEFAULT NULL COMMENT '积分消耗场景分布',
    `acquisition_channel_distribution` varchar(500) DEFAULT NULL COMMENT '积分获取渠道分布',
    `level_usage_distribution` varchar(500) DEFAULT NULL COMMENT '各等级积分使用情况',
    `remark` varchar(255) DEFAULT NULL COMMENT '分析备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_statistics_cycle` (`statistics_cycle`),
    KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分使用效率分析表';

CREATE TABLE `t_member_activity_heatmap` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `statistics_date` datetime NOT NULL COMMENT '统计日期',
    `statistics_dimension` int(11) NOT NULL COMMENT '统计维度：1-日期，2-小时，3-星期，4-月份',
    `active_member_count` int(11) DEFAULT 0 COMMENT '活跃会员数',
    `new_member_count` int(11) DEFAULT 0 COMMENT '新增会员数',
    `purchase_member_count` int(11) DEFAULT 0 COMMENT '消费会员数',
    `sign_in_member_count` int(11) DEFAULT 0 COMMENT '签到会员数',
    `point_usage_member_count` int(11) DEFAULT 0 COMMENT '积分使用会员数',
    `activity_index` int(11) DEFAULT 0 COMMENT '活跃度指数（0-100）',
    `hourly_distribution` varchar(500) DEFAULT NULL COMMENT '活跃时段分布',
    `level_distribution` varchar(500) DEFAULT NULL COMMENT '活跃等级分布',
    `region_distribution` varchar(500) DEFAULT NULL COMMENT '活跃区域分布',
    `mom_growth_rate` double DEFAULT 0.0 COMMENT '环比增长率（%）',
    `yoy_growth_rate` double DEFAULT 0.0 COMMENT '同比增长率（%）',
    `remark` varchar(255) DEFAULT NULL COMMENT '分析备注',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_statistics_date` (`statistics_date`),
    KEY `idx_statistics_dimension` (`statistics_dimension`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员活跃度热力图表';