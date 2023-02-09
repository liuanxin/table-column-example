package com.github.example.config;

import com.github.liuanxin.query.enums.TableRelationType;
import com.github.liuanxin.query.model.ReqModel;
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
    public Map<String, ReqModel> queryAliasMap() {
        return Map.of(
                "all-order", new ReqModel("Order"),

                "order-address-item-log", new ReqModel("Order", new ReqResult(List.of(
                        "orderNo", "orderStatus", "amount", "desc", "createTime",
                        Map.of("address", Map.of("table", "OrderAddress", "columns", List.of("contact", "phone", "address"))),
                        Map.of("items", Map.of("table", "OrderItem", "columns", List.of("productName", "price", "number"))),
                        Map.of("logs", Map.of("table", "OrderLog", "columns", List.of("operator", "message", "time")))
                ))));
    }
}
