package com.github.example.web;

import com.github.liuanxin.api.annotation.ApiGroup;
import com.github.liuanxin.api.annotation.ApiMethod;
import com.github.liuanxin.query.core.TableColumnTemplate;
import com.github.liuanxin.query.model.QueryInfo;
import com.github.liuanxin.query.model.ReqAlias;
import com.github.liuanxin.query.model.ReqInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiGroup("业务查增改删")
@RestController
@RequiredArgsConstructor
public class TableColumnController {

    private final TableColumnTemplate tableColumnTemplate;

    @PostMapping("/table-column-refresh")
    @ApiMethod(value = "刷新结构", index = 0)
    public Boolean refresh() {
        return tableColumnTemplate.refreshWithDatabase();
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
        POST /table-column
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

    @PostMapping("/table-column/{alias}")
    @ApiMethod( value = "数据查询(基于别名)", index = 3)
    public Object aliasQuery(@PathVariable("alias") String alias, @RequestBody ReqAlias req) {
        /*
        POST /table-column/order-address-item-log
        {
          "req" : {
            "query" : {
              "id" : 0,
              "orderNo": 1,
              "startTime": "2020-01-01"
            },
            "page": [2000]
          }
        }
        */
        return tableColumnTemplate.dynamicQuery(alias, req);
    }

    @PostMapping("/table-column-add")
    @ApiMethod( value = "数据添加", index = 4)
    public Object addTable(@RequestBody Object obj) {
        /*
        POST /table-column-add
        {
            "Order" : {
                "order_no": "订单号",
                "amount": "100.00",
                "desc": "备注1",
                "OrderAddress": {
                }
                "OrderItem": [{
                },{
                }]
            }
        }


        POST /table-column-add
        {
            "Order" : [{
                "order_no": "订单号",
                "amount": "200.00",
                "desc": "备注2",
                "OrderAddress": {
                }
                "OrderItem": [{
                },{
                }]
            }]
        }
        */
        return tableColumnTemplate.insertData(obj);
    }

    @PostMapping("/table-column-update")
    @ApiMethod( value = "数据更新", index = 5)
    public Object updateTable(@RequestBody Object obj) {
        /*
        POST /table-column/update
        {
            "Order" : {
                "id": "123",
                "desc": "备注"
            }
        }
        */
        return tableColumnTemplate.updateData(obj);
    }

    @PostMapping("/table-column-del")
    @ApiMethod( value = "数据删除", index = 6)
    public Object delTable(@RequestBody Object obj) {
        /*
        POST /table-column/del
        {
            "Order" : {
                "id": "123"
            }
        }
        */
        return tableColumnTemplate.deleteData(obj);
    }
}
