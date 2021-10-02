package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.model.Account;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class PaymentCommand implements Command {
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_AUTH);
        long userId = user.getId();
        List<Account> accounts = accountService.findAllAccountsByUserId(userId);
        req.setAttribute(Constant.ACCOUNTS, accounts);
        return "/jsp/make_payment.jsp";
    }
}
