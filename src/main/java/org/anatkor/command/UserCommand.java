package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.User;
import org.anatkor.model.enums.Role;
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
        User user = (User) session.getAttribute(Constant.USER_AUTH);
        if (req.getParameter("id") != null && "ADMIN".equals(session.getAttribute("role"))) {
            long id = Long.parseLong(req.getParameter(Constant.ID));
            try {
                user = userService.findUserById(id);
            } catch (DBException e) {
                req.setAttribute(Constant.WARN, e.getMessage());
            }
            if (req.getParameter("status") != null && !user.getRole().equals(Role.ADMIN)) {
                String redirect = changeUserIsActive(req, user);
                if (redirect != null) return redirect;
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
                log.info("User id = {} Active status updated", user.getId());
                return "redirect:/admin/user?id=" + user.getId() + "&message=updated";
            } else return "redirect:/admin/user?id=" + user.getId() + "&warn=not_updated";
        }
        return null;
    }
}
