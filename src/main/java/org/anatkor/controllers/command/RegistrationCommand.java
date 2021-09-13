package org.anatkor.controllers.command;

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
        String lastName = req.getParameter("last_name");
        String name = req.getParameter("name");
        String middleName = req.getParameter("middle_name");
        if (middleName == null) {
            middleName = "";
        }
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        if (req.getParameter("phone_number") != null) {
            long phoneNumber = Long.parseLong("38" + req.getParameter("phone_number"));
            try {
                if (userService.addUser(lastName, name, middleName, password, email, phoneNumber)) {
                    return "redirect:/login?message=registered&phone_number=" + req.getParameter("phone_number");
                }
                req.setAttribute("warn", "not_registered");
                return "/jsp/registration.jsp";
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
                req.setAttribute("last_name", lastName);
                req.setAttribute("name", name);
                req.setAttribute("middle_name", middleName);
                req.setAttribute("email", email);
            }
        }
        return "/jsp/registration.jsp";
    }
}
