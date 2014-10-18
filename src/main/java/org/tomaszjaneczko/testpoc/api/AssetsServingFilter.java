package org.tomaszjaneczko.testpoc.api;

import com.google.common.collect.Lists;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AssetsServingFilter implements Filter {

    public static final List<String> ALLOWED_PREFIXES = Lists.newArrayList(
        "/js/",
        "/css/",
        "/img/"
    );

    public static final List<String> ALLOWED_PATHS = Lists.newArrayList(
            "/favicon.ico",
            "/"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (matchesAllowedPaths(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/").forward(request, servletResponse);
        }
    }

    @Override
    public void destroy() {
        //
    }

    public boolean matchesAllowedPaths(String path) {
         return ALLOWED_PREFIXES.stream().anyMatch(path::startsWith)
            || ALLOWED_PATHS.stream().anyMatch(path::equals);
    }
}
