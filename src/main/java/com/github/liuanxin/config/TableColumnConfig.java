package com.github.liuanxin.config;

import com.github.liuanxin.query.annotation.EnableTableColumn;
import com.github.liuanxin.query.model.TableColumnRelation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTableColumn
public class TableColumnConfig {

    @Bean
    public List<TableColumnRelation> relationList() {
//        return Arrays.asList(
//                new TableColumnRelation("x", "xx", TableRelationType.ONE_TO_ONE, "y1", "xx"),
//                new TableColumnRelation("x", "xx", TableRelationType.ONE_TO_MANY, "y2", "xx")
//        );
        return new ArrayList<>();
    }
}
