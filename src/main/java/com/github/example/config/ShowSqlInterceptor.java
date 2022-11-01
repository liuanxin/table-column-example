package com.github.example.config;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.Query;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * mysql 5 的连接参数是: &statementInterceptors=com.github.common.sql.ShowSql5Interceptor
 * mysql 8 的连接参数是: &queryInterceptors=com.github.common.sql.ShowSqlInterceptor
 */
public class ShowSqlInterceptor implements QueryInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowSqlInterceptor.class);

    private static final Pattern BLANK_REGEX = Pattern.compile("\\s{1,}");

    @Override
    public QueryInterceptor init(MysqlConnection conn, Properties props, Log log) {
        return this;
    }

    @Override
    public <T extends Resultset> T preProcess(Supplier<String> sql, Query query) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("sql: {}", getRealSql(sql));
        }
        return null;
    }

    private String getRealSql(Supplier<String> sql) {
        // String realSql = SQLUtils.formatMySql(sql.replaceFirst("^\\s*?\n", ""));
        // String realSql = SqlFormat.format(sql.get().replaceFirst("^\\s*?\n", ""));
        String realSql = BLANK_REGEX.matcher(sql.get().replaceFirst("^\\s*?\n", "")).replaceAll(" ");
        int len = realSql.length(), max = 2000, leftRight = 400;
        return len > max ? (realSql.substring(0, leftRight) + " ...<" + len + ">... " + realSql.substring(len - leftRight, len)) : realSql;
    }

    @Override
    public <T extends Resultset> T postProcess(Supplier<String> sql, Query query, T rs, ServerSession serverSession) {
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() { return false; }
    @Override
    public void destroy() {}
}
