package org.anatkor.controllers.command;

import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class WalletCommand implements Command {
    private static final Logger log = LogManager.getLogger(WalletCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest req) {
        return "/jsp/index.jsp";
    }
}
