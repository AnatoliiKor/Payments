package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.model.Account;
import org.anatkor.services.AccountService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

class AdminCommand implements Command {
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        List<Account> accounts = accountService.findAllAccountsToDo();
        if (accounts != null && !accounts.isEmpty()) {
            req.setAttribute(Constant.ACCOUNTS, accounts);
        }
        return "/jsp/admin.jsp";
    }
}
