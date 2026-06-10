package com.scholaris.dto;

import java.util.List;
import java.util.Map;

public record QueryResult(
        String type,
        List<String> columns,
        List<Map<String, Object>> rows,
        int rowCount,
        String message
) {
    public static QueryResult select(List<String> columns, List<Map<String, Object>> rows) {
        return new QueryResult("SELECT", columns, rows, rows.size(), null);
    }

    public static QueryResult update(int affected) {
        return new QueryResult("UPDATE", null, null, affected,
                affected + " row(s) affected");
    }

    public static QueryResult error(String message) {
        return new QueryResult("ERROR", null, null, 0, message);
    }
}
