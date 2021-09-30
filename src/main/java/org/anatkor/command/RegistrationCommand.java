package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.exceptions.DBException;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegistrationCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        String lastName = req.getParameter(Constant.LAST_NAME);
        String name = req.getParameter(Constant.NAME);
        String middleName = req.getParameter(Constant.MIDDLE_NAME);
        if (middleName == null) {
            middleName = "";
        }
        String password = req.getParameter(Constant.PASSWORD);
        String email = req.getParameter(Constant.EMAIL);
        if (req.getParameter(Constant.PHONE_NUMBER) != null) {
            long phoneNumber = Long.parseLong("38" + req.getParameter(Constant.PHONE_NUMBER));
            try {
                if (userService.addUser(lastName, name, middleName, password, email, phoneNumber)) {
                    return "redirect:/login?message=registered&phone_number=" + req.getParameter(Constant.PHONE_NUMBER);
                }
                req.setAttribute(Constant.WARN, "not_registered");
                return "/jsp/registration.jsp";
            } catch (DBException e) {
                req.setAttribute(Constant.WARN, e.getMessage());
                req.setAttribute(Constant.LAST_NAME, lastName);
                req.setAttribute(Constant.NAME, name);
                req.setAttribute(Constant.MIDDLE_NAME, middleName);
                req.setAttribute(Constant.EMAIL, email);
            }
        }
        return "/jsp/registration.jsp";
    }
}
