package com.github.liuanxin.config;

import com.github.liuanxin.query.annotation.EnableTableColumn;
import com.github.liuanxin.query.enums.TableRelationType;
import com.github.liuanxin.query.model.TableColumnRelation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTableColumn
public class TableColumnConfig {

    @Bean
    public List<TableColumnRelation> relationList() {
        return Arrays.asList(
                new TableColumnRelation("t_inbound_order", "inbound_code", TableRelationType.ONE_TO_ONE, "t_inbound_transfer_extend", "inbound_code"),
                new TableColumnRelation("t_inbound_order", "inbound_code", TableRelationType.ONE_TO_MANY, "t_inbound_order_log", "inbound_code")
        );
    }
}
