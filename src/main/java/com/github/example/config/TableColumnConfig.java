package com.github.example.config;

import com.github.liuanxin.query.constant.QueryConst;
import com.github.liuanxin.query.enums.ConditionType;
import com.github.liuanxin.query.enums.OperateType;
import com.github.liuanxin.query.enums.TableRelationType;
import com.github.liuanxin.query.model.ReqAliasTemplate;
import com.github.liuanxin.query.model.ReqAliasTemplateQuery;
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
    public Map<String, ReqAliasTemplate> queryAliasMap() {
        return Map.of(
                "all-order", new ReqAliasTemplate("Order"),
                "order-address-item-log", orderAddressItemLogAlias()
        );
    }

    private ReqAliasTemplate orderAddressItemLogAlias() {
        // 指定查询条件及其表达式
        ReqAliasTemplateQuery query = new ReqAliasTemplateQuery(OperateType.AND, List.of(
                Map.of("id", ConditionType.$GT),
                Map.of("orderNo", ConditionType.$NN),
                Map.of(QueryConst.TEMPLATE_META_NAME, "startTime", "createTime", ConditionType.$GE),
                Map.of(QueryConst.TEMPLATE_META_NAME, "endTime", "createTime", ConditionType.$LE),
                new ReqAliasTemplateQuery(OperateType.OR, "x", List.of(
                        Map.of("orderStatus", ConditionType.$EQ),
                        Map.of("amount", ConditionType.$BET)
                )),
                Map.of("orderStatus", ConditionType.$IN),
                Map.of("OrderItem.productName", ConditionType.$NE),
                Map.of("OrderAddress.contact", ConditionType.$NE)
        ));
        // 指定默认的排序
        Map<String, String> sort = Map.of("createTime", "desc", "id", "asc");
        // 指定默认的分页
        List<String> page = List.of("1");
        // 指定查询时多张表之间的关联方式
        List<List<String>> relationList = List.of(List.of("Order", "inner", "OrderAddress"), List.of("Order", "left", "OrderItem"));
        // 指定返回字段: 订单表 + 订单地址表 订单项表 订单日志表, 查询 Order 时 distinct
        ReqResult result = new ReqResult(List.of(
                "orderNo", "orderStatus", "amount", "desc", "createTime",
                Map.of("address", Map.of("table", "OrderAddress", "columns", List.of("contact", "phone", "address"))),
                Map.of("items", Map.of("table", "OrderItem", "columns", List.of("productName", "price", "number"))),
                Map.of("logs", Map.of("table", "OrderLog", "columns", List.of("operator", "message", "time")))
        ), true);
        // return new ReqAliasTemplate("Order", query, relationList, result);
        return new ReqAliasTemplate("Order", query, sort, page, relationList, result);
    }
}
