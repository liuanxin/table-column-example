package com.github.example.config;

import com.github.liuanxin.query.enums.TableRelationType;
import com.github.liuanxin.query.model.ReqAlias;
import com.github.liuanxin.query.model.ReqResult;
import com.github.liuanxin.query.model.TableColumnRelation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class TableColumnConfig {

    @Bean
    public List<TableColumnRelation> tableRelationList() {
        return List.of(
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_ONE, "t_order_address", "order_no"),
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_MANY, "t_order_item", "order_no"),
                new TableColumnRelation("t_order", "order_no", TableRelationType.ONE_TO_MANY, "t_order_log", "order_no")
        );
    }

    @Bean
    public Map<String, ReqAlias> queryAliasMap() {
        return Map.of(
                "all-order", new ReqAlias("Order"),
                "order-address-item-log", orderAddressItemLogAlias()
        );
    }

    private ReqAlias orderAddressItemLogAlias() {
        // 指定 订单表 的字段, 指定 订单地址表 订单项表 订单日志表 的字段, 查询 Order 时 distinct, 并指定查询时多张表之间的关联方式
        ReqResult result = new ReqResult(List.of(
                "orderNo", "orderStatus", "amount", "desc", "createTime",
                Map.of("address", Map.of("table", "OrderAddress", "columns", List.of("contact", "phone", "address"))),
                Map.of("items", Map.of("table", "OrderItem", "columns", List.of("productName", "price", "number"))),
                Map.of("logs", Map.of("table", "OrderLog", "columns", List.of("operator", "message", "time")))
        ), true);
        List<List<String>> relationList = List.of(List.of("Order", "inner", "OrderAddress"), List.of("Order", "left", "OrderItem"));
        return new ReqAlias("Order", result, relationList);
    }
}
