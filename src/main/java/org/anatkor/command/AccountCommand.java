package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.services.AccountService;

import javax.servlet.http.HttpServletRequest;

class AccountCommand implements Command {
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        if (req.getParameter(Constant.ID) != null) {
            Long account_id = Long.parseLong(req.getParameter(Constant.ID));
            req.setAttribute(Constant.ACCOUNT, accountService.findById(account_id));
            return "/jsp/account.jsp";
        }
        return "redirect:/wallet";
    }
}
