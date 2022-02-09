package com.epam.javacourse.hotel.web.filter;

import com.epam.javacourse.hotel.web.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Security filter for access restriction.
 */
public class UserRoleFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(UserRoleFilter.class);

    private static final Map<String, List<String>> controlAccessMap = new HashMap<>();
    private List<String> common = new ArrayList<>();
    private List<String> noRole = new ArrayList<>();

    @Override
    public void init(FilterConfig config) {
        logger.debug("Filter initialization started");

        // roles
        controlAccessMap.put("client", toList(config.getInitParameter("client")));
        controlAccessMap.put("manager", toList(config.getInitParameter("manager")));
        logger.trace("Access map --> {}", controlAccessMap);

        // common
        common = toList(config.getInitParameter("common"));
        logger.trace("Common commands --> {}", common);

        // no role
        noRole = toList(config.getInitParameter("no-role"));
        logger.trace("No role commands --> {}", noRole);

        logger.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.debug("Filter started");

        if (accessAllowed(servletRequest)) {
            logger.debug("Filter finished");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessage = "You do not have enough permission to access the requested resource.";
            servletRequest.setAttribute("errorMessage", errorMessage);

            logger.trace("Set the request attribute: errorMessage --> {}", errorMessage);

            servletRequest.getRequestDispatcher(Path.PAGE_ERROR).forward(servletRequest, servletResponse);
        }
    }

    private boolean accessAllowed(ServletRequest servletRequest) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String commandName = servletRequest.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

//        if (noRole.contains(commandName)) {
//            return true;
//        }

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return false;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }

        return controlAccessMap.get(userRole).contains(commandName) || common.contains(commandName) || noRole.contains(commandName);
    }

    @Override
    public void destroy() {
        logger.debug("Filter destruction started");
        logger.debug("Filter destruction finished");
    }

    private List<String> toList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(param);
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }
        return list;
    }
}
