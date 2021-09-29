package org.anatkor.command;

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
        String page = req.getParameter("pg");
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }
        List<User> users = userService.findAll();
        int pgMax = 1 + users.size() / 10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute("users", users);
        return "/jsp/users_list.jsp";
    }
}
