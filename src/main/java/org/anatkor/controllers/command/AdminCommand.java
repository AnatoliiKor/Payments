package org.anatkor.controllers.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.Role;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class AdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(AdminCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        return "/jsp/admin.jsp";
    }
}
