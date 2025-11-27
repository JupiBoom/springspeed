-- Create database if not exists
CREATE DATABASE IF NOT EXISTS springspeed CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE springspeed;

-- User Table
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别: 0-未知, 1-男, 2-女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `region` varchar(100) DEFAULT NULL COMMENT '地域',
  `occupation` varchar(100) DEFAULT NULL COMMENT '职业',
  `income_level` varchar(50) DEFAULT NULL COMMENT '收入水平',
  `interest_tags` text COMMENT '兴趣标签',
  `recency` int(11) DEFAULT NULL COMMENT '最近一次消费时间(天)',
  `frequency` int(11) DEFAULT NULL COMMENT '消费频率',
  `monetary` decimal(10,2) DEFAULT NULL COMMENT '消费金额',
  `rfm_score` int(11) DEFAULT NULL COMMENT 'RFM总分',
  `user_segment` varchar(50) DEFAULT NULL COMMENT '用户细分',
  `last_active_time` datetime DEFAULT NULL COMMENT '最后活跃时间',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0-禁用, 1-启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_last_active` (`last_active_time`),
  KEY `idx_rfm_score` (`rfm_score`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- Category Table
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '分类级别: 1-一级, 2-二级, 3-三级',
  `description` text COMMENT '分类描述',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0-禁用, 1-启用',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- Product Table
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `brand` varchar(100) DEFAULT NULL COMMENT '品牌',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `sales_volume` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `average_rating` decimal(3,2) DEFAULT '0.00' COMMENT '平均评分',
  `tags` text COMMENT '商品标签',
  `keywords` text COMMENT '搜索关键词',
  `image_url` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0-下架, 1-上架',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_brand` (`brand`),
  KEY `idx_sales_volume` (`sales_volume`),
  KEY `idx_view_count` (`view_count`),
  KEY `idx_average_rating` (`average_rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- User Behavior Table
CREATE TABLE IF NOT EXISTS `user_behavior` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `behavior_type` tinyint(4) NOT NULL COMMENT '行为类型: 1-浏览, 2-收藏, 3-加入购物车, 4-购买, 5-评分, 6-评论',
  `behavior_weight` int(11) NOT NULL DEFAULT '1' COMMENT '行为权重',
  `rating` tinyint(4) DEFAULT NULL COMMENT '评分(1-5星)',
  `page_source` varchar(100) DEFAULT NULL COMMENT '页面来源',
  `stay_time` int(11) DEFAULT NULL COMMENT '停留时间(秒)',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_behavior_type` (`behavior_type`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行为表';

-- Order Table
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态: 1-待支付, 2-已支付, 3-已发货, 4-已完成, 5-已取消',
  `payment_method` tinyint(4) DEFAULT NULL COMMENT '支付方式: 1-微信, 2-支付宝, 3-银行卡',
  `shipping_address` text COMMENT '收货地址',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- Order Item Table
CREATE TABLE IF NOT EXISTS `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) NOT NULL COMMENT '商品名称',
  `unit_price` decimal(10,2) NOT NULL COMMENT '单价',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `total_price` decimal(10,2) NOT NULL COMMENT '总价',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- Cart Item Table
CREATE TABLE IF NOT EXISTS `cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `unit_price` decimal(10,2) NOT NULL COMMENT '单价',
  `is_checked` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否选中: 0-未选中, 1-选中',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车项表';

-- Recommendation Result Table
CREATE TABLE IF NOT EXISTS `recommendation_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `recommend_type` tinyint(4) NOT NULL COMMENT '推荐类型: 1-首页推荐, 2-购物车推荐, 3-商品详情推荐, 4-基于内容推荐, 5-热门推荐',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `recommend_score` decimal(5,4) NOT NULL DEFAULT '0.0000' COMMENT '推荐分数',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '推荐排名',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_type` (`user_id`, `recommend_type`),
  KEY `idx_recommend_type` (`recommend_type`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐结果表';

-- Recommendation Effect Table
CREATE TABLE IF NOT EXISTS `recommendation_effect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `recommend_type` tinyint(4) NOT NULL COMMENT '推荐类型',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `exposure_time` datetime DEFAULT NULL COMMENT '曝光时间',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  `purchase_time` datetime DEFAULT NULL COMMENT '购买时间',
  `click_to_purchase_interval` int(11) DEFAULT NULL COMMENT '点击到购买的时间间隔(秒)',
  `is_clicked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否点击: 0-否, 1-是',
  `is_purchased` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否购买: 0-否, 1-是',
  `revenue` decimal(10,2) DEFAULT '0.00' COMMENT '产生的收益',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_recommend_type` (`recommend_type`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_exposure_time` (`exposure_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐效果分析表';

-- Insert initial data
INSERT INTO `user` (`username`, `email`, `password`, `gender`, `age`, `region`, `occupation`, `income_level`) 
VALUES 
('user1', 'user1@example.com', '123456', 1, 25, '北京', '程序员', '中高收入'),
('user2', 'user2@example.com', '123456', 2, 30, '上海', '教师', '中等收入'),
('user3', 'user3@example.com', '123456', 1, 35, '广州', '医生', '高收入'),
('user4', 'user4@example.com', '123456', 2, 28, '深圳', '设计师', '中高收入'),
('user5', 'user5@example.com', '123456', 1, 40, '杭州', '企业家', '高收入');

INSERT INTO `category` (`name`, `parent_id`, `level`, `description`) 
VALUES 
('电子产品', NULL, 1, '电子产品分类'),
('手机', 1, 2, '手机分类'),
('电脑', 1, 2, '电脑分类'),
('服装', NULL, 1, '服装分类'),
('男装', 4, 2, '男装分类'),
('女装', 4, 2, '女装分类'),
('家电', NULL, 1, '家电分类'),
('冰箱', 7, 2, '冰箱分类'),
('洗衣机', 7, 2, '洗衣机分类');

INSERT INTO `product` (`name`, `description`, `category_id`, `brand`, `price`, `stock`, `tags`, `keywords`, `image_url`) 
VALUES 
('iPhone 15 Pro', '苹果最新旗舰手机', 2, 'Apple', 7999.00, 100, '手机,苹果,旗舰,5G', 'iPhone,苹果手机,旗舰手机', 'https://example.com/iphone15pro.jpg'),
('Samsung Galaxy S24', '三星旗舰手机', 2, 'Samsung', 6999.00, 80, '手机,三星,旗舰,5G', 'Galaxy,S24,三星手机', 'https://example.com/s24.jpg'),
('MacBook Pro 14', '苹果笔记本电脑', 3, 'Apple', 14999.00, 50, '电脑,苹果,笔记本,Pro', 'MacBook,苹果电脑,笔记本电脑', 'https://example.com/macbookpro.jpg'),
('Dell XPS 13', '戴尔轻薄本', 3, 'Dell', 8999.00, 60, '电脑,戴尔,轻薄本', 'Dell,XPS,轻薄本', 'https://example.com/xps13.jpg'),
('Nike Air Max', '耐克运动鞋', 5, 'Nike', 799.00, 200, '男装,运动鞋,耐克', 'Nike,Air Max,运动鞋', 'https://example.com/airmax.jpg'),
('Adidas Ultraboost', '阿迪达斯跑鞋', 5, 'Adidas', 899.00, 150, '男装,跑鞋,阿迪达斯', 'Adidas,Ultraboost,跑鞋', 'https://example.com/ultraboost.jpg'),
('Levi\'s 牛仔裤', '李维斯牛仔裤', 5, 'Levi\'s', 599.00, 180, '男装,牛仔裤,李维斯', 'Levi\'s,牛仔裤,男装', 'https://example.com/levis.jpg'),
('海尔冰箱', '海尔智能冰箱', 8, 'Haier', 3999.00, 40, '家电,冰箱,海尔,智能', '海尔,冰箱,智能冰箱', 'https://example.com/haier-fridge.jpg'),
('西门子洗衣机', '西门子滚筒洗衣机', 9, 'Siemens', 4999.00, 30, '家电,洗衣机,西门子,滚筒', '西门子,洗衣机,滚筒洗衣机', 'https://example.com/siemens-washer.jpg'),
('小米电视', '小米智能电视', 7, 'Xiaomi', 2999.00, 70, '家电,电视,小米,智能', '小米,电视,智能电视', 'https://example.com/xiaomi-tv.jpg');

-- Insert some user behavior data
INSERT INTO `user_behavior` (`user_id`, `product_id`, `behavior_type`, `behavior_weight`, `rating`) 
VALUES 
(1, 1, 1, 1, NULL), -- user1浏览iPhone 15 Pro
(1, 1, 2, 5, NULL), -- user1收藏iPhone 15 Pro
(1, 3, 1, 1, NULL), -- user1浏览MacBook Pro
(1, 5, 1, 1, NULL), -- user1浏览Nike Air Max
(2, 2, 1, 1, NULL), -- user2浏览Samsung S24
(2, 2, 4, 10, 5), -- user2购买Samsung S24并评分5星
(2, 6, 1, 1, NULL), -- user2浏览Adidas Ultraboost
(3, 3, 1, 1, NULL), -- user3浏览MacBook Pro
(3, 3, 4, 10, 4), -- user3购买MacBook Pro并评分4星
(3, 8, 1, 1, NULL), -- user3浏览海尔冰箱
(4, 4, 1, 1, NULL), -- user4浏览Dell XPS 13
(4, 7, 1, 1, NULL), -- user4浏览Levi\'s牛仔裤
(4, 7, 3, 3, NULL), -- user4加入购物车
(5, 9, 1, 1, NULL), -- user5浏览西门子洗衣机
(5, 10, 1, 1, NULL), -- user5浏览小米电视
(5, 10, 4, 10, 5); -- user5购买小米电视并评分5星