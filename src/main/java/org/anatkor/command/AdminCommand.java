package org.anatkor.command;

import org.anatkor.model.Account;
import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
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
