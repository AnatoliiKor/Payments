package org.anatkor.command;

import org.anatkor.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class LogoutCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user_auth") != null) {
            User user = (User) session.getAttribute("user_auth");
            log.info("User {} is Logged out", user.getPhoneNumber());
            session.invalidate();
        }
        return "redirect:/login";
    }
}
