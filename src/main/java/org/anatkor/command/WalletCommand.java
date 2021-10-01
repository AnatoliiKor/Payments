package org.anatkor.command;

import org.anatkor.model.Account;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class WalletCommand implements Command {
    private static final Logger log = LogManager.getLogger(WalletCommand.class);
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_auth");
        List<Account> accounts = accountService.findAllAccountsByUserId(user.getId());
        req.setAttribute("user_id", user.getId());
        req.setAttribute("accounts", accounts);
        return "/jsp/index.jsp";
    }
}
