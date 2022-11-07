package com.github.example.config;

import com.github.liuanxin.query.enums.TableRelationType;
import com.github.liuanxin.query.model.TableColumnRelation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TableColumnConfig {

    @Bean
    public List<TableColumnRelation> relationList() {
//        String order = QueryLambdaUtil.toTableName(Order::getId);
//        String orderNo = QueryLambdaUtil.toColumnName(Order::getOrderNo);
//
//        String orderAddress = QueryLambdaUtil.toTableName(OrderAddress::getId);
//        String orderAddressOrderNo = QueryLambdaUtil.toColumnName(OrderAddress::getOrderNo);
//
//        String orderItem = QueryLambdaUtil.toTableName(OrderItem::getId);
//        String orderItemOrderNo = QueryLambdaUtil.toColumnName(OrderItem::getOrderNo);
//
//        String orderLog = QueryLambdaUtil.toTableName(OrderLog::getId);
//        String orderLogOrderNo = QueryLambdaUtil.toColumnName(OrderLog::getOrderNo);
//
//        return Arrays.asList(
//                new TableColumnRelation(order, orderNo, TableRelationType.ONE_TO_ONE, orderAddress, orderAddressOrderNo),
//                new TableColumnRelation(order, orderNo, TableRelationType.ONE_TO_MANY, orderItem, orderItemOrderNo),
//                new TableColumnRelation(order, orderNo, TableRelationType.ONE_TO_MANY, orderLog, orderLogOrderNo)
//        );

        return Arrays.asList(
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_ONE, "t_order_address", "order_no"),
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_MANY, "t_order_item", "order_no"),
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_MANY, "t_order_log", "order_no")
        );
    }
}
