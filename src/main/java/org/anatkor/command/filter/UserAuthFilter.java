package org.anatkor.command.filter;

import org.anatkor.constants.Constant;
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
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (session.getAttribute(Constant.USER_AUTH) == null) {
            resp.sendRedirect("/login");
            return;
        }

        if (session.getAttribute(Constant.USER_AUTH) != null) {
            User userAuth = (User) session.getAttribute(Constant.USER_AUTH);
            Role role = userAuth.getRole();
            if (req.getParameter(Constant.USER_ID) != null) {
                long user_id = Long.parseLong(req.getParameter(Constant.USER_ID));
                if (userAuth.getId() != user_id && role != Role.ADMIN) {
                    resp.sendRedirect("/login");
                    return;
                }
            }
            if (req.getParameter(Constant.ACCOUNT_NUMBER) != null && role != Role.ADMIN) {
                if (!checkIsUserAccount(req, userAuth)) {
                    resp.sendRedirect("/login");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean checkIsUserAccount(HttpServletRequest req, User userAuth) {
        long account_number = Long.parseLong(req.getParameter(Constant.ACCOUNT_NUMBER));
        List<Long> userAuthAccountNumbers = userAuth.getAccountNumbers();
        for (long number : userAuthAccountNumbers) {
            if (number == account_number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
