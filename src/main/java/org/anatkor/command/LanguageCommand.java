package org.anatkor.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;

class LanguageCommand implements Command {
    private static final Logger log = LogManager.getLogger(LanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        final String language = request.getParameter("language");
        HttpSession session = request.getSession();
        session.setAttribute("language", language);
        try {
            String path = new URL(request.getHeader("Referer")).getFile();
            return "redirect:" + path;
        } catch (MalformedURLException e) {
            log.debug(e.getMessage());
        }
        return "jsp/index.jsp";
    }
}
