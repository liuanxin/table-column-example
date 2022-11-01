
DROP TABLE IF EXISTS `t_buyer`;
CREATE TABLE IF NOT EXISTS `t_buyer` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除, 1.已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='买家';


DROP TABLE IF EXISTS `t_order`;
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '买家 id',
  `seller_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '卖家 id',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `desc` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_status` int unsigned NOT NULL DEFAULT '0' COMMENT '订单状态(0.用户已创建待支付, 1.用户已支付待商户发货, 2.商户已发货待用户签收, 3.用户已签收待确认完结, 4.已完结)',
  `amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `deleted` int unsigned NOT NULL DEFAULT '0' COMMENT '0.未删除, 非 0.已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`,`deleted`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_seller_id` (`seller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单';


DROP TABLE IF EXISTS `t_order_address`;
CREATE TABLE IF NOT EXISTS `t_order_address` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `contact` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人',
  `phone` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `province` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人省',
  `city` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人市',
  `area` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人区',
  `address` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人地址(不含省市区)',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单地址';


DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE IF NOT EXISTS `t_order_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `product_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `price` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `number` int unsigned NOT NULL DEFAULT '0' COMMENT '商品数量',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单项(商品)';


DROP TABLE IF EXISTS `t_order_log`;
CREATE TABLE IF NOT EXISTS `t_order_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `operate_name` bigint unsigned NOT NULL DEFAULT '0' COMMENT '操作人',
  `operate_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `message` text COLLATE utf8mb4_unicode_ci COMMENT '操作内容',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单日志';


DROP TABLE IF EXISTS `t_seller`;
CREATE TABLE IF NOT EXISTS `t_seller` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '店铺名',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `deleted` int unsigned NOT NULL DEFAULT '0' COMMENT '0.未删除, 非 0.已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shop_name` (`shop_name`,`deleted`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='卖家';
