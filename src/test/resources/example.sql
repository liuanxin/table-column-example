
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `order_status` int unsigned NOT NULL DEFAULT '0' COMMENT '订单状态(0.用户已创建待支付, 1.用户已支付待商户发货, 2.商户已发货待用户签收, 3.用户已签收待确认完结, 4.已完结)',
  `amount` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `desc` varchar(32) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `deleted` int unsigned NOT NULL DEFAULT '0' COMMENT '0.未删除, 非 0.已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单';

INSERT INTO `t_order` (`order_no`, `order_status`, `amount`) VALUES
('1-2-221010-00001', 1, 125.49), ('1-2-221010-00002', 2, 38.00);


DROP TABLE IF EXISTS `t_order_address`;
CREATE TABLE IF NOT EXISTS `t_order_address` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `contact` varchar(16) NOT NULL DEFAULT '' COMMENT '联系人',
  `phone` varchar(16) NOT NULL DEFAULT '' COMMENT '联系电话',
  `address` varchar(128) NOT NULL DEFAULT '' COMMENT '联系人地址',
  `create_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `update_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `deleted` bigint unsigned NOT NULL DEFAULT '0' COMMENT '0.未删除, 非 0.已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单地址';

INSERT INTO `t_order_address` (`order_no`, `contact`, `phone`, `address`) VALUES
('1-2-221010-00001', '张三', '13012345678', 'xxx');


DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE IF NOT EXISTS `t_order_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `product_name` varchar(32) NOT NULL DEFAULT '' COMMENT '商品名',
  `price` decimal(20,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `number` int unsigned NOT NULL DEFAULT '0' COMMENT '商品数量',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单项(商品)';

INSERT INTO `t_order_item` (`order_no`, `product_name`, `price`, `number`) VALUES
('1-2-221010-00001', 'iPhone 14 Pro', 8999, 1),
('1-2-221010-00001', '女款外套', 129.5, 2);


DROP TABLE IF EXISTS `t_order_log`;
CREATE TABLE IF NOT EXISTS `t_order_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
  `operator` varchar(32) NOT NULL DEFAULT '' COMMENT '操作人',
  `message` text COMMENT '操作内容',
  `time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0.未删除, 1.已删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单日志';

INSERT INTO `t_order_log` (`order_no`, `operator`, `message`, `time`) VALUES
('1-2-221010-00001', '张三', '创建订单', '2022-10-01 11:12:13'),
('1-2-221010-00001', '张三', '支付成功', '2022-10-01 12:13:14');
