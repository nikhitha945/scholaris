package com.scholaris.service;

import com.scholaris.dto.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminQueryService {

    private static final Logger log = LoggerFactory.getLogger(AdminQueryService.class);

    private final DataSource dataSource;
    private final int timeoutSeconds;
    private final int maxRows;

    public AdminQueryService(
            DataSource dataSource,
            @Value("${app.admin.query.timeout-seconds:10}") int timeoutSeconds,
            @Value("${app.admin.query.max-rows:500}") int maxRows
    ) {
        this.dataSource = dataSource;
        this.timeoutSeconds = timeoutSeconds;
        this.maxRows = maxRows;
    }

    public QueryResult execute(String sql, String username) {
        log.info("[admin-sql] user={} sql={}", username, sql);
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.setQueryTimeout(timeoutSeconds);
            stmt.setMaxRows(maxRows);

            boolean isResultSet = stmt.execute(sql);
            if (isResultSet) {
                return mapResultSet(stmt.getResultSet());
            } else {
                return QueryResult.update(stmt.getUpdateCount());
            }
        } catch (Exception e) {
            log.error("[admin-sql] user={} error={}", username, e.getMessage());
            return QueryResult.error(e.getMessage());
        }
    }

    private QueryResult mapResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();

        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= colCount; i++) {
            columns.add(meta.getColumnLabel(i));
        }

        List<Map<String, Object>> rows = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= colCount; i++) {
                row.put(columns.get(i - 1), rs.getObject(i));
            }
            rows.add(row);
        }
        return QueryResult.select(columns, rows);
    }
}
