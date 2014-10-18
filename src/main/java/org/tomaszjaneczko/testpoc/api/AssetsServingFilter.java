package org.tomaszjaneczko.testpoc.api;

import com.google.common.collect.Lists;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Filter used to render main index.html in servlet set up by AssetsBundle.
 *
 * All requests that don't fetch .js, .css or images, go to the same file,
 * which is the entry point to single-page applications.
 *
 * Assumption here is that assets servlet is set up correctly and already
 * lets through all API requests to respective Resources.
 *
 * Filter is set up in Dropwizard's Application subclass.
 */
public class AssetsServingFilter implements Filter {

    /**
     * List of prefixes that should be let through.
     */
    public static final List<String> ALLOWED_PREFIXES = Lists.newArrayList(
        "/js/",
        "/css/",
        "/img/"
    );

    /**
     * List of full paths that should be let through, including the root.
     */
    public static final List<String> ALLOWED_PATHS = Lists.newArrayList(
            "/favicon.ico",
            "/"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (matchesAllowedPaths(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            // Request should be forwarded to the root.
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    public boolean matchesAllowedPaths(String path) {
         return ALLOWED_PREFIXES.stream().anyMatch(path::startsWith)
            || ALLOWED_PATHS.stream().anyMatch(path::equals);
    }
}
