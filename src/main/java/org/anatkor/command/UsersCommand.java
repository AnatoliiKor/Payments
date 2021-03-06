package org.anatkor.command;

import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.anatkor.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

class UsersCommand implements Command {
    private final UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        Util.requestGetAndSetPage(req);
        List<User> users = userService.findAll();
        int pgMax = 1 + users.size() / 10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute("users", users);
        return "/jsp/users_list.jsp";
    }
}
