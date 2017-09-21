package de.scout24.analyser.configuration;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static de.scout24.analyser.ApplicationConstant.*;

@Component
public class SimpleCORSFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin"));
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Accept, X-Requested-With, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
