package com.github.example.web;

import com.github.liuanxin.api.annotation.ApiGroup;
import com.github.liuanxin.api.annotation.ApiMethod;
import com.github.liuanxin.query.core.TableColumnTemplate;
import com.github.liuanxin.query.model.QueryInfo;
import com.github.liuanxin.query.model.ReqInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiGroup("表字段")
@RestController
@RequiredArgsConstructor
public class TableColumnController {

//    private static final TypeReference<List<Map<String, Object>>> TYPE_REFERENCE = new TypeReference<List<Map<String, Object>>>() {};

    private final TableColumnTemplate tableColumnTemplate;

    @GetMapping("/table-column-refresh")
    @ApiMethod(value = "刷新结构", index = 0)
    public String refresh() {
        boolean flag = tableColumnTemplate.refreshWithDatabase();
        return flag ? "success" : "fail";
    }

    @GetMapping("/table-column")
    @ApiMethod(value = "结构查询", index = 1)
    public List<QueryInfo> info(String tables) {
        return tableColumnTemplate.info(tables);
    }

    @PostMapping("/table-column")
    @ApiMethod( value = "数据查询", index = 2)
    public Object query(@RequestBody ReqInfo req) {
        /*
        {
          "table": "Order",
          "param": {
            "query": {
              "conditions": [
                [ "id", "$gt", 0 ],
                [ "orderNo", "$nn" ],
                [ "createTime", "$ge", "2020-01-01" ],
                [ "OrderItem.productName", "$ne", "" ],
                [ "OrderAddress.contact", "$ne", "" ]
              ]
            },
            "sort": { "createTime": "desc" },
            "page": [ 1 ],
            "relation": [ [ "Order", "inner", "OrderAddress" ],  [ "Order", "left", "OrderItem" ] ]
          },
          "result": {
            "columns": [
              "orderNo", "orderStatus", "amount", "desc", "createTime",
              {
                "address": {
                  "table": "OrderAddress",
                  "columns": [  "contact", "phone", "address" ]
                }
              },
              {
                "items": {
                  "table": "OrderItem",
                  "columns": [ "productName", "price",  "number" ]
                }
              },
              {
                "logs": {
                  "table": "OrderLog",
                  "columns": [ "operator", "message", "time" ]
                }
              }
            ],
            "distinct": true
          }
        }
        */
        return tableColumnTemplate.dynamicQuery(req);
    }

    @PostMapping("/table-column-alias")
    @ApiMethod( value = "别名查询", index = 3)
    public Object aliasQuery(@RequestBody ReqInfo req) {
        /*
        {
          "alias" : "order-address-item-log",
          "aliasQuery" : {
            "query" : {
              "id" : 0,
              "orderNo": null,
              "startTime": "2020-01-01",
              "OrderItem.productName": "",
              "OrderAddress.contact": ""
            },
            "sort": { "createTime": "desc" },
            "page" : [ 1 ]
          }
        }
        */
        return tableColumnTemplate.dynamicQuery(req);
    }

//    @GetMapping("/generate-user")
//    @ApiMethod(value = "生成数据", index = 4)
//    public String generate(@ApiParam("表名") String tables, @ApiParam("生成个数") Integer count) {
//        int flag = 0;
//        if (tables != null && !tables.trim().isEmpty() && count != null && count > 0) {
//            for (String table : tables.split(",")) {
//                table = table.trim();
//                Table tableInfo = tableColumnTemplate.getTcInfo().findTable(table);
//                if (tableInfo != null) {
//                    List<Map<String, Object>> dataList = new ArrayList<>();
//                    for (int i = 0; i < count; i++) {
//                        Map<String, Object> data = new HashMap<>();
//                        List<String> idKeyList = tableInfo.getIdKey();
//                        for (TableColumn tableColumn : tableInfo.getColumnMap().values()) {
//                            if (!idKeyList.contains(tableColumn.getName())) {
//                                String name = tableColumn.getAlias();
//                                Object info;
//                                Class<?> fieldType = tableColumn.getFieldType();
//                                String lowerCase = name.toLowerCase().replace("_", "");
//                                if (name.equals(tableInfo.getLogicColumn())) {
//                                    info = tableInfo.getLogicValue();
//                                } else if (fieldType == Boolean.class) {
//                                    info = GenerateUtil.toBoolean();
//                                } else if (fieldType == Integer.class) {
//                                    info = GenerateUtil.toInt(10);
//                                } else if (fieldType == Long.class) {
//                                    info = GenerateUtil.toLong(30000L);
//                                } else if (fieldType == BigDecimal.class) {
//                                    info = GenerateUtil.toDecimal(50000D, 2);
//                                } else if (fieldType == Date.class) {
//                                    if ("createtime".equals(lowerCase)) {
//                                        info = GenerateUtil.toDate(120);
//                                    } else if ("updatetime".equals(lowerCase)) {
//                                        info = GenerateUtil.toDate(10);
//                                    } else {
//                                        info = GenerateUtil.toDate(150);
//                                    }
//                                } else {
//                                    if (lowerCase.contains("username")) {
//                                        info = GenerateUtil.toName();
//                                    } else if (lowerCase.contains("phone") || lowerCase.contains("tel")) {
//                                        info = GenerateUtil.toPhone();
//                                    } else if (lowerCase.contains("email")) {
//                                        info = GenerateUtil.toEmail();
//                                    } else if (lowerCase.contains("code")) {
//                                        info = GenerateUtil.toCode(null);
//                                    } else {
//                                        info = GenerateUtil.toVarchar(10);
//                                    }
//                                }
//                                data.put(name, info);
//                            }
//                        }
//                        if (QueryUtil.isNotEmpty(data)) {
//                            dataList.add(data);
//                        }
//                    }
//                    flag += tableColumnTemplate.insertBatch(table, dataList);
//                }
//            }
//        }
//        return "生成成功 " + flag + " 条";
//    }
//
//    @PostMapping("/generate-user")
//    @ApiMethod(value = "直接生成数据", index = 5)
//    public String generate(@RequestBody Map<String, Object> req) {
//        String table = QueryUtil.toStr(req.get("table"));
//        List<Map<String, Object>> dataList = QueryJsonUtil.convertType(req.get("data"), TYPE_REFERENCE);
//        int flag = tableColumnTemplate.insertBatch(table, dataList);
//        return "成功 " + flag + " 条";
//    }
}
