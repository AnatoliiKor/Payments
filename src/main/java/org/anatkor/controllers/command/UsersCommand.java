package org.anatkor.controllers.command;

import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

class UsersCommand implements Command {
    private static final Logger log = LogManager.getLogger(UsersCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        log.info("users list requested");
        List<User> users = userService.findAll();
        req.setAttribute("users", users);
        return "/jsp/users_list.jsp";
    }
}
