package org.anatkor.command.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class MessagesFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getParameter("message") != null) {
            req.setAttribute("message", req.getParameter("message"));
        }
        if (req.getParameter("warn") != null) {
            req.setAttribute("warn", req.getParameter("warn"));
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
    }
}
