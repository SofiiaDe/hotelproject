package com.epam.javacourse.hotel.db;

public class Helpers {

    private Helpers() {
    }

    public static String enrichWithPageSizeStatement(int page, int pageSize, String sql) {
        String result = sql;
        if (page > 0){
            result += " LIMIT " + pageSize;
            result += page > 1 ? " OFFSET " + (page - 1) * pageSize : "";
        }
        return result;
    }
}
