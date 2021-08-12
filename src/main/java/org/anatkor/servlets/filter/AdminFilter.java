package org.anatkor.servlets.filter;

import org.anatkor.servlets.UsersServlet;
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
    public void doFilter(ServletRequest request, ServletResponse respond,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) respond;
        HttpSession session = req.getSession();
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            log.warn("attempt of unauthorized access to the Admin by {}", session.getAttribute("user_auth"));
            req.setAttribute("warn", "Access is forbidden. Log in as Admin ");
//            session.setAttribute("req_uri", req.getRequestURI());
            req.getRequestDispatcher("/login").forward(req, resp);
        }
        chain.doFilter(request, respond);
    }

    @Override
    public void destroy() {
    }
}
