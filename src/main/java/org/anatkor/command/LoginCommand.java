package org.anatkor.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.enums.Role;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        String phone = req.getParameter("phone_number");
        String password = req.getParameter("password");

        if (phone != null && password == null) {
            req.setAttribute("message", req.getParameter("message"));
            req.setAttribute("phone_number", req.getParameter("phone_number"));
            return "/jsp/login.jsp";
        }
        if ((phone == null || phone.isEmpty()) && (password == null || password.isEmpty())) {
            log.info("Not enough login data");
            return "/jsp/login.jsp";
        } else {
            try {
                Long phoneNumber = Long.parseLong("38" + phone);
                User user = userService.findUserByPhoneAndPassword(phoneNumber, password);
                HttpSession session = req.getSession();
                session.setAttribute("user_auth", user);
                session.setAttribute("user_auth_id", user.getId());
                session.setAttribute("role", (user.getRole()).name());
                if (user.getRole().equals(Role.ADMIN)) {
                    log.info("Admin '{}' is logged in", phoneNumber);
                    return "redirect:/admin";
                } else {
                    log.info("Client '{}' is logged in", phoneNumber);
                    List<Long> accountNumbers = accountService.findAllAccountNumbersByUserId(user.getId());
                    session.setAttribute("account_numbers", accountNumbers);
                    return "redirect:/wallet";
                }
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
                return "/jsp/login.jsp";
            }
        }
    }
}
