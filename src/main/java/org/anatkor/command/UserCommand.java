package org.anatkor.command;

import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class UserCommand implements Command {
    private static final Logger log = LogManager.getLogger(UserCommand.class);
    private final UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_auth");
        log.info("User {} profile called", user.getPhoneNumber());
        if (req.getParameter("id") != null && "ADMIN".equals(session.getAttribute("role"))) {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                user = userService.findUserById(id);
            } catch (DBException e) {
                req.setAttribute("warn", e.getMessage());
            }
            if (req.getParameter("status") != null && !user.getRole().name().equals("ADMIN")) {
                String x = changeUserIsActive(req, user);
                if (x != null) return x;
            }
        }
        req.setAttribute("user", user);
        return "/jsp/user.jsp";
    }

    private String changeUserIsActive(HttpServletRequest req, User user) {
        boolean profileStatus = Boolean.parseBoolean(req.getParameter("status"));
        if (Boolean.compare(user.isActive(), profileStatus) != 0) {
            user.setActive(profileStatus);
            if (userService.updateUserStatus(user)) {
                return "redirect:/admin/user?id=" + user.getId() + "&message=updated";
            } else return "redirect:/admin/user?id=" + user.getId() + "&warn=not_updated";
        }
        return null;
    }
}
