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
              "orderNo": 1,
              "startTime": "2020-01-01"
            },
            "page": [2000]
          }
        }
        */
        return tableColumnTemplate.dynamicQuery(req);
    }
}
