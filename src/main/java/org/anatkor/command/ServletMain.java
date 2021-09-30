package org.anatkor.command;

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
public class ServletMain extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger(ServletMain.class);

    @Override
    public void init() {
        commands.put("account", new AccountCommand());
        commands.put("active_account", new ActiveAccountCommand());
        commands.put("new_account", new NewAccountCommand());
        commands.put("refill_account", new RefillAccountCommand());
        commands.put("accounts", new AccountsCommand());
        commands.put("admin", new AdminCommand());
        commands.put("exception", new ExceptionCommand());
        commands.put("lang", new LanguageCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("payment", new PaymentCommand());
        commands.put("registration", new RegistrationCommand());
//        commands.put("transaction", new TransactionCommand());
        commands.put("transactions", new TransactionsCommand());
        commands.put("wallet", new WalletCommand());
        commands.put("user", new UserCommand());
        commands.put("users", new UsersCommand());
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
        Command command = commands.getOrDefault(path, (r) -> "/jsp/login.jsp");
        String urlPage = command.execute(req);
        if (urlPage.contains("redirect:")) {
            log.debug("redirect to=" + urlPage.replaceAll("redirect:", ""));
            resp.sendRedirect(urlPage.replaceAll("redirect:", ""));
        } else {
            log.debug("forward to=" + urlPage);
            req.getRequestDispatcher(urlPage).forward(req, resp);
        }
    }
}
