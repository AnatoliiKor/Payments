package org.anatkor.controllers.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class AdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(AdminCommand.class);
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        List<Account> accounts = accountService.findAllAccountsToDo();
        if (accounts != null && !accounts.isEmpty()) {
            req.setAttribute("accounts", accounts);
        }
        return "/jsp/admin.jsp";
    }
}
