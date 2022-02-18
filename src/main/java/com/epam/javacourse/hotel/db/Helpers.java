package com.epam.javacourse.hotel.db;

/**
 * Helper for building SQL-queries.
 */
public class Helpers {

    private Helpers() {
    }

    /**
     * Enriches given SQL-query with LIMIT value (max number of rows returned in a query result)
     * and OFFSET value (specify which row to start from retrieving data)
     * @param page current page
     * @param pageSize as configured
     * @param sql given SQL-query
     * @return
     */
    public static String enrichWithPageSizeStatement(int page, int pageSize, String sql) {
        String result = sql;
        if (page > 0){
            result += " LIMIT " + pageSize;
            result += page > 1 ? " OFFSET " + (page - 1) * pageSize : "";
        }
        return result;
    }
}
