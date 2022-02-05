package com.epam.javacourse.hotel.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);
    private String encoding;

    /**
     * Init method.
     */
    @Override
    public void init(FilterConfig config) {
        logger.debug("Filter initialization starts");
        encoding = config.getInitParameter("encoding");
        logger.trace("Encoding from web.xml --> " + encoding);
        logger.debug("Filter initialization finished");
    }

    /**
     * Main method.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.debug("Filter starts");

        String requestEncoding = request.getCharacterEncoding();

        if (requestEncoding == null) {
            logger.trace("Request encoding is null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }
        logger.debug("Filter finished");
        chain.doFilter(request, response);
    }

    /**
     * Destroy method.
     */
    @Override
    public void destroy() {
        logger.debug("Filter destruction starts");
        // do nothing
        logger.debug("Filter destruction finished");
    }
}

