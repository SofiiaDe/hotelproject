package com.epam.javacourse.hotel.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding filter
 */
public class EncodingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        logger.debug("Filter initialization started");
        encoding = config.getInitParameter("encoding");
        logger.trace("Encoding from web.xml --> {}", encoding);
        logger.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("Filter started");
        String servletRequestEncoding = servletRequest.getCharacterEncoding();

        if (servletRequestEncoding == null) {
            logger.trace("Request encoding is null, set encoding --> {}", encoding);
            servletRequest.setCharacterEncoding(encoding);
        }
        logger.debug("Filter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("Filter destruction started");
        logger.debug("Filter destruction finished");
    }
}

