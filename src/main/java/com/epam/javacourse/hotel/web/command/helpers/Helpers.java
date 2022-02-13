package com.epam.javacourse.hotel.web.command.helpers;

import javax.servlet.http.HttpServletRequest;

public class Helpers {
    public static int parsePage(HttpServletRequest request){
        String pageParam = request.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 1;
        return page <= 0 ? 1: page;
    }
}
