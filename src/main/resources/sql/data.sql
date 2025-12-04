-- 初始化会员等级配置
INSERT INTO `t_member_level_config` (`level_code`, `level_name`, `required_growth_value`, `valid_days`, `discount_rate`, `monthly_freight_coupon_count`, `priority_shipping`, `birthday_point_multiple`, `customer_service_level`, `description`)
VALUES
(1, '普通会员', 0, 365, 100, 0, 0, 1, '普通客服', '注册即可成为普通会员'),
(2, '白银会员', 1000, 365, 95, 1, 0, 2, '高级客服', '累计成长值达到1000'),
(3, '黄金会员', 5000, 365, 90, 2, 1, 3, '专属客服', '累计成长值达到5000'),
(4, '铂金会员', 20000, 365, 85, 5, 1, 5, 'VIP客服', '累计成长值达到20000');

-- 初始化积分规则配置
INSERT INTO `t_point_rule_config` (`rule_type`, `rule_name`, `point_ratio`, `daily_limit`, `monthly_limit`, `expire_days`, `deduct_ratio`, `sign_in_base_points`, `continuous_sign_in_extra_points`, `description`, `status`)
VALUES
(1, '消费积分规则', 1, 5000, 20000, 365, 100, 0, 0, '每消费1元获得1积分', 1),
(2, '签到积分规则', 0, 50, 1000, 365, 0, 10, 5, '每日签到获得10积分，连续签到额外获得5积分', 1),
(3, '活动积分规则', 0, 1000, 5000, 365, 0, 0, 0, '活动奖励积分规则', 1),
(4, '积分抵扣规则', 0, 0, 0, 0, 100, 0, 0, '100积分抵扣1元', 1);

-- 初始化会员权益
INSERT INTO `t_member_benefit` (`benefit_name`, `benefit_type`, `applicable_levels`, `benefit_value`, `issue_cycle`, `monthly_issue_count`, `limit_count_per_person`, `valid_days`, `anti_brush_config`, `description`, `status`)
VALUES
('普通会员折扣', 1, '1', 100, 1, 1, 0, 365, '无', '普通会员不享受折扣', 1),
('白银会员95折', 1, '2', 95, 1, 1, 0, 365, '无', '白银会员享受95折优惠', 1),
('黄金会员9折', 1, '3', 90, 1, 1, 0, 365, '无', '黄金会员享受9折优惠', 1),
('铂金会员85折', 1, '4', 85, 1, 1, 0, 365, '无', '铂金会员享受85折优惠', 1),
('白银会员运费券', 2, '2', 10, 3, 1, 0, 7, '每日限领1张', '白银会员每月1张10元运费券', 1),
('黄金会员运费券', 2, '3', 10, 3, 2, 0, 7, '每日限领1张', '黄金会员每月2张10元运费券', 1),
('铂金会员运费券', 2, '4', 15, 3, 5, 0, 7, '每日限领1张', '铂金会员每月5张15元运费券', 1),
('优先发货权益', 3, '3,4', 0, 1, 1, 0, 365, '无', '黄金及以上会员享受优先发货', 1),
('生日特权', 4, '1,2,3,4', 0, 4, 1, 0, 7, '每人每年1次', '生日特权', 1),
('专属客服权益', 5, '3,4', 0, 1, 1, 0, 365, '无', '黄金及以上会员享受专属客服', 1);

-- 初始化生日特权配置
INSERT INTO `t_birthday_privilege_config` (`level_code`, `point_reward`, `birthday_discount`, `coupon_amount`, `issue_days_in_advance`, `valid_days`, `description`, `status`)
VALUES
(1, 100, 100, 0, 3, 7, '普通会员生日特权', 1),
(2, 200, 95, 20, 3, 7, '白银会员生日特权', 1),
(3, 500, 90, 50, 3, 7, '黄金会员生日特权', 1),
(4, 1000, 85, 100, 3, 7, '铂金会员生日特权', 1);

-- 初始化客服分配
INSERT INTO `t_customer_service_allocation` (`service_id`, `service_name`, `service_level`, `responsible_levels`, `allocated_member_count`, `max_member_count`, `online_status`, `description`, `status`)
VALUES
(1, '张三', '普通', '1', 0, 1000, 1, '负责普通会员客服', 1),
(2, '李四', '高级', '2', 0, 800, 1, '负责白银会员客服', 1),
(3, '王五', '专属', '3', 0, 500, 1, '负责黄金会员客服', 1),
(4, '赵六', 'VIP', '4', 0, 200, 1, '负责铂金会员客服', 1);

-- 初始化测试会员
INSERT INTO `t_member` (`member_no`, `username`, `phone`, `email`, `level_code`, `growth_value`, `valid_start_time`, `valid_end_time`, `birthday`, `customer_service_id`, `status`)
VALUES
('M20240001', 'testuser1', '13800138001', 'test1@example.com', 1, 500, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), '1990-01-15', 1, 1),
('M20240002', 'testuser2', '13800138002', 'test2@example.com', 2, 1500, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), '1992-05-20', 2, 1),
('M20240003', 'testuser3', '13800138003', 'test3@example.com', 3, 6000, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), '1988-08-10', 3, 1),
('M20240004', 'testuser4', '13800138004', 'test4@example.com', 4, 25000, NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), '1995-11-25', 4, 1);

-- 初始化测试积分账户
INSERT INTO `t_point_account` (`member_id`, `total_points`, `available_points`, `frozen_points`, `expired_points`)
VALUES
(1, 1000, 1000, 0, 0),
(2, 3000, 2800, 200, 0),
(3, 8000, 7500, 500, 0),
(4, 15000, 14000, 1000, 0);