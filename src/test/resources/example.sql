
drop database if exists `table-column-example`;
create database `table-column-example`;
use `table-column-example`;


drop table if exists `t_order`;
create table `t_order` (
  `id` bigint unsigned not null auto_increment,
  `order_no` varchar(32) not null comment '订单号',
  `order_status` int unsigned not null default '0' comment '订单状态(0.用户已创建待支付, 1.用户已支付待商户发货, 2.商户已发货待用户签收, 3.用户已签收待确认完结, 4.已完结)',
  `amount` decimal(20,2) unsigned not null default '0.00' comment '订单金额',
  `desc` varchar(32) not null default '' comment '备注',
  `create_time` datetime(6) not null default current_timestamp(6) comment '创建时间',
  `update_time` datetime(6) not null default current_timestamp(6) on update current_timestamp(6) comment '更新时间',
  `deleted` int unsigned not null default '0' comment '0.未删除, 非 0.已删除',
  primary key (`id`),
  unique key `uk_order_no` (`order_no`,`deleted`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单';

insert into `t_order` (`order_no`, `order_status`, `amount`, `desc`) values
('1-2-221010-00001', 1, 9258.00, '请尽快发货'),
('1-2-221010-00002', 2, 6900.00, '');


drop table if exists `t_order_address`;
create table `t_order_address` (
  `id` bigint unsigned not null auto_increment,
  `order_no` varchar(32) not null default '' comment '订单号',
  `contact` varchar(16) not null default '' comment '联系人',
  `phone` varchar(16) not null default '' comment '联系电话',
  `address` varchar(128) not null default '' comment '联系人地址',
  `create_time` datetime(6) not null default current_timestamp(6) comment '创建时间',
  `update_time` datetime(6) not null default current_timestamp(6) on update current_timestamp(6) comment '更新时间',
  `deleted` bigint unsigned not null default '0' comment '0.未删除, 非 0.已删除',
  primary key (`id`),
  unique key `uk_order_no` (`order_no`,`deleted`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单地址';

insert into `t_order_address` (`order_no`, `contact`, `phone`, `address`) values
('1-2-221010-00001', '张三', '13012345678', '北京市朝阳区'),
('1-2-221010-00002', '李四', '13122223333', '广东省广州市白云区');


drop table if exists `t_order_item`;
create table `t_order_item` (
  `id` bigint unsigned not null auto_increment,
  `order_no` varchar(32) not null default '' comment '订单号',
  `product_name` varchar(32) not null default '' comment '商品名',
  `price` decimal(20,2) unsigned not null default '0.00' comment '商品价格',
  `number` int unsigned not null default '0' comment '商品数量',
  primary key (`id`),
  key `idx_order_no` (`order_no`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单项(商品)';

insert into `t_order_item` (`order_no`, `product_name`, `price`, `number`) values
('1-2-221010-00001', 'iphone 14 pro', 8999, 1),
('1-2-221010-00001', '女款外套', 129.5, 2),
('1-2-221010-00002', 'samsung', 6999.00, 1);


drop table if exists `t_order_log`;
create table `t_order_log` (
  `id` bigint unsigned not null auto_increment,
  `order_no` varchar(32) not null default '' comment '订单号',
  `operator` varchar(32) not null default '' comment '操作人',
  `message` text comment '操作内容',
  `time` datetime(6) not null default current_timestamp(6) comment '创建时间',
  `deleted` tinyint(1) not null default '0' comment '0.未删除, 1.已删除',
  primary key (`id`),
  key `idx_order_no` (`order_no`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='订单日志';

insert into `t_order_log` (`order_no`, `operator`, `message`, `time`) values
('1-2-221010-00001', '张三', '创建订单', '2022-10-01 11:12:13'),
('1-2-221010-00001', '张三', '支付成功', '2022-10-01 12:13:14'),
('1-2-221010-00002', '李四', '创建订单', '2022-10-01 12:15:14'),
('1-2-221010-00002', '李四', '支付成功', '2022-10-01 12:16:14'),
('1-2-221010-00002', '李四', '已发货, 待用户收货', '2022-10-01 12:17:14');
