package com.github.example;

import com.github.example.util.GenerateUtil;
import com.github.liuanxin.query.core.TableColumnTemplate;
import com.github.liuanxin.query.model.Table;
import com.github.liuanxin.query.model.TableColumn;
import com.github.liuanxin.query.model.TableColumnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest(classes = WebApplication.class)
public class DataGenerateTest {

    @Autowired
    private TableColumnTemplate tableColumnTemplate;

    @Test
    public void generateData() {
        int count = 120000;

        String order = "Order";
        String orderAddress = "OrderAddress";
        String orderItem = "OrderItem";
        String orderLog = "OrderLog";

        TableColumnInfo columnInfo = tableColumnTemplate.getTcInfo();
        Table orderTable = columnInfo.findTable(order);
        Table orderAddressTable = columnInfo.findTable(orderAddress);
        Table orderItemTable = columnInfo.findTable(orderItem);
        Table orderLogTable = columnInfo.findTable(orderLog);

        List<Map<String, Object>> orderList = new ArrayList<>();
        List<Map<String, Object>> orderAddressList = new ArrayList<>();
        List<Map<String, Object>> orderItemList = new ArrayList<>();
        List<Map<String, Object>> orderLogList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Map<String, Object> orderInfo = fillData(orderTable);
            orderList.add(orderInfo);

            Map<String, Object> address = fillData(orderAddressTable);
            address.put("orderNo", orderInfo.get("orderNo"));
            orderAddressList.add(address);

            int itemCount = GenerateUtil.randomInt(3);
            for (int j = 0; j < itemCount; j++) {
                Map<String, Object> item = fillData(orderItemTable);
                item.put("orderNo", orderInfo.get("orderNo"));
                orderItemList.add(item);
            }

            int logCount = GenerateUtil.randomInt(5);
            for (int j = 0; j < logCount; j++) {
                Map<String, Object> log = fillData(orderLogTable);
                log.put("orderNo", orderInfo.get("orderNo"));
                orderLogList.add(log);
            }
        }
        int orderCount = tableColumnTemplate.insertBatch(order, orderList);
        int addressCount = tableColumnTemplate.insertBatch(orderAddress, orderAddressList);
        int itemCount = tableColumnTemplate.insertBatch(orderItem, orderItemList);
        int logCount = tableColumnTemplate.insertBatch(orderLog, orderLogList);
        System.out.printf("order: %s, address: %s, item: %s, log: %s\n", orderCount, addressCount, itemCount, logCount);
    }

    private Map<String, Object> fillData(Table tableInfo) {
        Map<String, Object> data = new HashMap<>();
        List<String> idKeyList = tableInfo.getIdKey();
        for (TableColumn tableColumn : tableInfo.getColumnMap().values()) {
            if (!idKeyList.contains(tableColumn.getName())) {
                String name = tableColumn.getAlias();
                Object info;
                Class<?> fieldType = tableColumn.getFieldType();
                String lowerCase = name.toLowerCase().replace("_", "");
                if (name.equals(tableInfo.getLogicColumn())) {
                    info = tableInfo.getLogicValue();
                } else if (fieldType == Boolean.class) {
                    info = GenerateUtil.randomBoolean();
                } else if (fieldType == Integer.class) {
                    info = GenerateUtil.randomInt(8);
                } else if (fieldType == Long.class) {
                    info = GenerateUtil.randomLong(30000L);
                } else if (fieldType == BigDecimal.class) {
                    info = GenerateUtil.randomDecimal(1000D, 2);
                } else if (fieldType == Date.class) {
                    if ("createtime".equals(lowerCase)) {
                        info = GenerateUtil.randomDate(120);
                    } else if ("updatetime".equals(lowerCase)) {
                        info = GenerateUtil.randomDate(10);
                    } else {
                        info = GenerateUtil.randomDate(150);
                    }
                } else {
                    if (lowerCase.contains("username") || lowerCase.contains("contact") || lowerCase.contains("operator")) {
                        info = GenerateUtil.randomName();
                    } else if (lowerCase.contains("phone") || lowerCase.contains("tel")) {
                        info = GenerateUtil.randomPhone();
                    } else if (lowerCase.contains("email")) {
                        info = GenerateUtil.randomEmail();
                    } else if (lowerCase.contains("desc")) {
                        info = GenerateUtil.randomDesc();
                    } else if (lowerCase.contains("address")) {
                        info = GenerateUtil.randomAddress();
                    } else if (lowerCase.contains("product")) {
                        info = GenerateUtil.randomProduct();
                    } else if (lowerCase.contains("msg") || lowerCase.contains("message")) {
                        info = GenerateUtil.randomMsg();
                    } else if (lowerCase.contains("code") || lowerCase.contains("no")) {
                        info = GenerateUtil.randomCode(null);
                    } else {
                        info = GenerateUtil.randomVarchar(10);
                    }
                }
                data.put(name, info);
            }
        }
        return data;
    }
}
