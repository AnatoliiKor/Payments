package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class NewAccountCommand implements Command {
    private static final Logger log = LogManager.getLogger(NewAccountCommand.class);
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Long user_id = (Long) session.getAttribute("user_auth_id");
        log.info("request for a new account by user id={}", user_id);
        String accountName = req.getParameter(Constant.ACCOUNT_NAME);
        String currency = req.getParameter(Constant.CURRENCY);
        if (accountName != null && !"".equals(accountName)) {
            if (accountService.newAccount(accountName, currency, user_id)) {
                return "redirect:/wallet/accounts?message=account_opened&user_id=" + user_id;
            } else return "redirect:/wallet?warn=account_not_opened";
        }
        return "redirect:/wallet";
    }
}
