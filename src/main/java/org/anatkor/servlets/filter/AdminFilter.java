package org.anatkor.servlets.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    final static Logger log = LogManager.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            log.warn("Attempt of unauthorized access to the Admin by {}", session.getAttribute("user_auth"));
            req.setAttribute("warn", "Access is forbidden. Log in as Admin ");
//            session.setAttribute("req_uri", req.getRequestURI());
            req.getRequestDispatcher("/login").forward(req, resp);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
