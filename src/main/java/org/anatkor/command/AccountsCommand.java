package org.anatkor.command;

import org.anatkor.model.Account;
import org.anatkor.model.enums.Role;
import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class AccountsCommand implements Command {
    private static final Logger log = LogManager.getLogger(AccountsCommand.class);
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
        if (role == Role.ADMIN && req.getParameter("user_id") == null) {
            log.info("account list requested by ADMIN");
            user_id = -1L;
        } else {
            user_id = Long.parseLong(req.getParameter("user_id"));
            log.info("account list requested for user with id= {}", user_id);
        }
        return user_id;
    }

    private String getRequestParamSort(HttpServletRequest req) {
        String sortBy;
        if (req.getParameter("sort_by") != null) {
            sortBy = req.getParameter("sort_by");
        } else {
            sortBy = "number";
        }
        return sortBy;
    }

    private String getRequestParamOrder(HttpServletRequest req) {
        String order;
        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
        } else {
            order = "ASC";
        }
        return order;
    }

    private HttpServletRequest fillRequest(HttpServletRequest req, String sortBy, String order, long user_id, List<Account> accounts) {
        String page = req.getParameter("pg");
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }
        int pgMax = 1 + accounts.size() / 10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute("user_id", user_id);
        req.setAttribute("accounts", accounts);
        req.setAttribute("sort_by", sortBy);
        req.setAttribute("order", order);
        return req;
    }
}
