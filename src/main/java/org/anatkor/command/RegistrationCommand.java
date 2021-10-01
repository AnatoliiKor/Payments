package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegistrationCommand.class);
    private final UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        User user = userService.userFromRequest(req);
        if (user != null) {
            try {
                if (userService.addUser(user)) {
                    return "redirect:/login?message=registered_mes";
                }
                req.setAttribute(Constant.WARN, "not_registered");
                return "/jsp/registration.jsp";
            } catch (DBException e) {
                req.setAttribute(Constant.WARN, e.getMessage());
                log.info("User {} is not registered due to DBException {}", user.getPhoneNumber(), e.getMessage());
                fillRequest(req, user);
            }
        }
        return "/jsp/registration.jsp";
    }

    private HttpServletRequest fillRequest(HttpServletRequest req, User user) {
        req.setAttribute(Constant.LAST_NAME, user.getLastName());
        req.setAttribute(Constant.NAME, user.getName());
        req.setAttribute(Constant.MIDDLE_NAME, user.getMiddleName());
        req.setAttribute(Constant.EMAIL, user.getEmail());
        return req;
    }
}
