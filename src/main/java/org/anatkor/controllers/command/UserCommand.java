package org.anatkor.controllers.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class UserCommand implements Command {
    private static final Logger log = LogManager.getLogger(UserCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_auth");
        log.info("User {} profile called", user.getPhoneNumber());
        if (req.getParameter("id") != null && "ADMIN".equals(session.getAttribute("role"))) {
            Long id = Long.valueOf(req.getParameter("id"));
            try {
                user = userService.findUserById(id);
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
            }
            if (req.getParameter("status") != null && !user.getRole().name().equals("ADMIN")) {
                boolean profileStatus = Boolean.parseBoolean(req.getParameter("status"));
                if (Boolean.compare(user.isActive(), profileStatus) != 0) {
                    user.setActive(profileStatus);
                    if (userService.updateUserStatus(user)) {
                        return "redirect:/admin/user?id=" + id + "&message=updated";
                    } else return "redirect:/admin/user?id=" + id + "&warn=not_updated";
                }
            }
        }
        req.setAttribute("user", user);
        return "/jsp/user.jsp";
    }
}
