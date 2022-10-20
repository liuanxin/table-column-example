package com.github.liuanxin.web;

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

@RestController
@RequiredArgsConstructor
public class IndexExampleController {

    private final TableColumnTemplate tcTemplate;

    @GetMapping("/table-column")
    public JsonResult<List<QueryInfo>> tcInfo(String tables) {
        return JsonResult.success("table-column info", tcTemplate.info(tables));
    }

    @PostMapping("/table-column")
    public JsonResult<Object> query(@RequestBody RequestInfo req) {
        return JsonResult.success("query table-column", tcTemplate.dynamicQuery(req));
    }
}
