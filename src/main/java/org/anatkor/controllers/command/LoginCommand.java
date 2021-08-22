package org.anatkor.controllers.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        String phone = req.getParameter("phone_number");
        String password = req.getParameter("password");

        if ((phone == null || phone.isEmpty()) && (password == null || password.isEmpty())) {
            log.info("Not enough login data");
//            CommandUtils.addBackUrl(req);
            return "/jsp/login.jsp";
        } else {
            try {
                Long phoneNumber = Long.parseLong("38" + phone);
                User user = userService.findUserByPhoneAndPassword(phoneNumber, password);
                HttpSession session = req.getSession();
                session.setAttribute("user_auth", user);
                session.setAttribute("role", (user.getRole()).name());
                if (user.getRole().equals(Role.ADMIN)) {
                    log.info("Admin '{}' is logged in", phoneNumber);
                    return "redirect:/admin";
                } else {
                    log.info("Client '{}' is logged in", phoneNumber);
                    return "redirect:/wallet";
                }
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
                return "/jsp/login.jsp";
            }
        }
    }
}
