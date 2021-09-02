package org.anatkor.controllers.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class ServletController extends HttpServlet{
    private Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ServletController.class);

    @Override
    public void init() {
        commands.put("lang", new Language());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("admin", new AdminCommand());
        commands.put("wallet", new WalletCommand());
        commands.put("user", new UserCommand());
        commands.put("users", new UsersCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("account", new AccountCommand());
        commands.put("accounts", new AccountsCommand());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getRequestURI();
        path = path.replaceAll(".*(/wallet)?(/admin)?/", "");
        Command command = commands.getOrDefault(path, (r)->"/jsp/login.jsp");
        String urlPage = command.execute(req);
        if (urlPage.contains("redirect:")) {
            log.debug("redirect to=" + urlPage.replaceAll("redirect:", ""));
            resp.sendRedirect(urlPage.replaceAll("redirect:", "")); //replaceAll("redirect:", "/app")
        } else {
            log.debug("forward to=" + urlPage);
            req.getRequestDispatcher(urlPage).forward(req, resp);
        }
    }
}
