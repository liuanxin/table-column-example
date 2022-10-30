package com.github.liuanxin.web;

import com.github.liuanxin.api.annotation.ApiGroup;
import com.github.liuanxin.api.annotation.ApiMethod;
import com.github.liuanxin.api.annotation.ApiParam;
import com.github.liuanxin.query.core.TableColumnTemplate;
import com.github.liuanxin.query.model.QueryInfo;
import com.github.liuanxin.query.model.RequestInfo;
import com.github.liuanxin.query.model.Table;
import com.github.liuanxin.query.model.TableColumn;
import com.github.liuanxin.query.util.QueryUtil;
import com.github.liuanxin.util.GenerateUtil;
import com.github.liuanxin.util.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@ApiGroup("表字段")
@RestController
@RequiredArgsConstructor
public class TableColumnController {

    private final TableColumnTemplate tableColumnTemplate;

    @GetMapping("/generate-user")
    @ApiMethod(value = "生成数据", index = 2)
    public JsonResult<String> example(@ApiParam("表名") String tables, @ApiParam("生成个数") Integer count) {
        if (tables != null && !tables.trim().isEmpty() && count != null && count > 0) {
            for (String table : tables.split(",")) {
                table = table.trim();
                Table tableInfo = tableColumnTemplate.getTcInfo().findTable(table);
                if (tableInfo != null) {
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
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
                                    info = GenerateUtil.toBoolean();
                                } else if (fieldType == Integer.class) {
                                    info = GenerateUtil.toInt(10);
                                } else if (fieldType == Long.class) {
                                    info = GenerateUtil.toLong(30000L);
                                } else if (fieldType == BigDecimal.class) {
                                    info = GenerateUtil.toDecimal(50000D, 2);
                                } else if (fieldType == Date.class) {
                                    if ("createtime".equals(lowerCase)) {
                                        info = GenerateUtil.toDate(120);
                                    } else if ("updatetime".equals(lowerCase)) {
                                        info = GenerateUtil.toDate(10);
                                    } else {
                                        info = GenerateUtil.toDate(150);
                                    }
                                } else {
                                    if (lowerCase.contains("username")) {
                                        info = GenerateUtil.toName();
                                    } else if (lowerCase.contains("phone") || lowerCase.contains("tel")) {
                                        info = GenerateUtil.toPhone();
                                    } else if (lowerCase.contains("email")) {
                                        info = GenerateUtil.toEmail();
                                    } else if (lowerCase.contains("code")) {
                                        info = GenerateUtil.toCode(null);
                                    } else {
                                        info = GenerateUtil.toVarchar(10);
                                    }
                                }
                                data.put(name, info);
                            }
                        }
                        if (QueryUtil.isNotEmpty(data)) {
                            dataList.add(data);
                        }
                    }
                    tableColumnTemplate.insertBatch(table, dataList);
                }
            }
        }
        return JsonResult.success("操作成功");
    }

    @GetMapping("/table-column")
    @ApiMethod(value = "结构查询", index = 1)
    public JsonResult<List<QueryInfo>> info(String tables) {
        return JsonResult.success("table-column info", tableColumnTemplate.info(tables));
    }

    @PostMapping("/table-column")
    @ApiMethod(value = "数据查询", index = 3)
    public JsonResult<Object> query(@RequestBody RequestInfo req) {
        return JsonResult.success("query table-column", tableColumnTemplate.dynamicQuery(req));
    }
}
