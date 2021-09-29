package org.anatkor.controllers.command.filter;

import org.anatkor.model.User;
import org.anatkor.model.enums.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/wallet/*")
public class UserAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (session.getAttribute("user_auth") == null) {
            resp.sendRedirect("/login");
            return;
        }

        if (session.getAttribute("user_auth") != null) {
            User userAuth = (User) session.getAttribute("user_auth");
            Role role = userAuth.getRole();
            if (req.getParameter("user_id") != null) {
                Long user_id = Long.parseLong(req.getParameter("user_id"));
                if (userAuth.getId() != user_id && role != Role.ADMIN) {
                    resp.sendRedirect("/login");
                    return;
                }
            }
            if (req.getParameter("account_number") != null && role != Role.ADMIN) {
                Long account_number = Long.parseLong(req.getParameter("account_number"));
                List<Long> userAuthAccountNumbers = userAuth.getAccountNumbers();
                boolean permission = false;
                for (long number : userAuthAccountNumbers) {
                    if (number == account_number) {
                        permission = true;
                    }
                }
                if (!permission) {
                    resp.sendRedirect("/login");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
