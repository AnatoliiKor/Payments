package org.anatkor.command.filter;

import org.anatkor.constants.Constant;

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
        if (req.getParameter(Constant.MESSAGE) != null) {
            req.setAttribute(Constant.MESSAGE, req.getParameter(Constant.MESSAGE));
        }
        if (req.getParameter(Constant.WARN) != null) {
            req.setAttribute(Constant.WARN, req.getParameter(Constant.WARN));
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {
    }
}
