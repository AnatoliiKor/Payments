package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.model.Account;
import org.anatkor.model.enums.Role;
import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class AccountsCommand implements Command {
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        long userId = getRequestParamUserId(req);
        String order = getRequestParamOrder(req);
        String sortBy = getRequestParamSort(req);
        List<Account> accounts = accountService.findAllAccountsByUserIdSorted(userId, sortBy, order);
        fillRequest(req, sortBy, order, userId, accounts);
        return "/jsp/accounts_list.jsp";
    }

    private long getRequestParamUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Role role = Role.valueOf((String) session.getAttribute("role"));
        long user_id;
        if (role == Role.ADMIN && req.getParameter(Constant.USER_ID) == null) {
            user_id = -1L;
        } else {
            user_id = Long.parseLong(req.getParameter(Constant.USER_ID));
        }
        return user_id;
    }

    private String getRequestParamSort(HttpServletRequest req) {
        String sortBy;
        if (req.getParameter(Constant.SORT_BY) != null) {
            sortBy = req.getParameter(Constant.SORT_BY);
        } else {
            sortBy = Constant.NUMBER;
        }
        return sortBy;
    }

    private String getRequestParamOrder(HttpServletRequest req) {
        String order;
        if (req.getParameter(Constant.ORDER) != null) {
            order = req.getParameter(Constant.ORDER);
        } else {
            order = "ASC";
        }
        return order;
    }

    private HttpServletRequest fillRequest(HttpServletRequest req, String sortBy, String order, long user_id, List<Account> accounts) {
        String page = req.getParameter(Constant.PAGE);
        if (page == null || page.equals("")) {
            req.setAttribute(Constant.PAGE, 1);
        } else {
            req.setAttribute(Constant.PAGE, Integer.parseInt(page));
        }
        int pgMax = 1 + accounts.size() / 10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute(Constant.USER_ID, user_id);
        req.setAttribute(Constant.ACCOUNTS, accounts);
        req.setAttribute(Constant.SORT_BY, sortBy);
        req.setAttribute(Constant.ORDER, order);
        return req;
    }
}
