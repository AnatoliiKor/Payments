package org.anatkor.controllers.command;

import org.anatkor.model.Account;
import org.anatkor.model.Bike;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class AccountsCommand implements Command {
    private static final Logger log = LogManager.getLogger(AccountsCommand.class);
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        String sortBy;
        String order;
        Long user_id;
        HttpSession session = req.getSession();
        if (req.getParameter("user_id")==null){
            user_id = (Long) session.getAttribute("user_auth_id");
        } else {
            user_id = Long.parseLong(req.getParameter("user_id"));
        }
        log.info("account list requested for uder with id= {}", user_id);
        if (req.getParameter("sort_by") != null) {
            sortBy = req.getParameter("sort_by");
            session.setAttribute("sort_by", sortBy);
        } else if (session.getAttribute("sort_by") != null) {
            sortBy = (String) session.getAttribute("sort_by");
        } else {
            sortBy = "balance";
        }

        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
            session.setAttribute("order", order);
        } else if (session.getAttribute("order") != null) {
            order = (String) session.getAttribute("order");
        } else {
            order = "DESC";
        }
        List<Account> accounts = accountService.findAllByUserId(user_id, sortBy, order);
        req.setAttribute("accounts", accounts);
        req.setAttribute("sort_by", sortBy);
        req.setAttribute("order", order);
        return "/jsp/accounts_list.jsp";

//        String page = req.getParameter("pg");
//        if (page == null || page.equals("")) {
//            req.setAttribute("pg", 1);
//        } else {
//            req.setAttribute("pg", Integer.parseInt(page));
//        }
//
//        List<Bike> bikes = bikeService.findAll(sortBy, order);
//        req.setAttribute("bikes", bikes);
//        req.getRequestDispatcher("/jsp/bikes_list.jsp").forward(req, resp);
//    }

    }
}
