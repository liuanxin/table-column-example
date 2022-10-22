package com.github.liuanxin.web;

import com.github.liuanxin.api.annotation.ApiGroup;
import com.github.liuanxin.api.annotation.ApiMethod;
import com.github.liuanxin.query.core.TableColumnTemplate;
import com.github.liuanxin.query.model.QueryInfo;
import com.github.liuanxin.query.model.RequestInfo;
import com.github.liuanxin.util.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiGroup("表字段")
@RestController
@RequiredArgsConstructor
public class IndexExampleController {

    private final TableColumnTemplate tcTemplate;

    @GetMapping("/table-column")
    @ApiMethod(value = "结构查询", index = 1)
    public JsonResult<List<QueryInfo>> info(String tables) {
        return JsonResult.success("table-column info", tcTemplate.info(tables));
    }

    @PostMapping("/table-column")
    @ApiMethod(value = "数据查询", index = 2)
    public JsonResult<Object> query(@RequestBody RequestInfo req) {
        return JsonResult.success("query table-column", tcTemplate.dynamicQuery(req));
    }
}
