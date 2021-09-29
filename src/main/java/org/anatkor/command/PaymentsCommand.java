package org.anatkor.command;

import org.anatkor.model.Payment;
import org.anatkor.model.enums.Role;
import org.anatkor.services.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class PaymentsCommand implements Command {
    private static final Logger log = LogManager.getLogger(PaymentsCommand.class);
    private TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        String sortBy;
        String order;
        long user_id;
        long account_number;
        List<Payment> payments;
        HttpSession session = req.getSession();
        Role role = Role.valueOf((String) session.getAttribute("role"));

        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
            session.setAttribute("order", order);
        } else if (session.getAttribute("order") != null) {
            order = (String) session.getAttribute("order");
        } else {
            order = "DESC";
        }

        if (req.getParameter("payment_sort_by") != null) {
            sortBy = req.getParameter("payment_sort_by");
            session.setAttribute("payment_sort_by", sortBy);
        } else if (session.getAttribute("payment_sort_by") != null) {
            sortBy = (String) session.getAttribute("payment_sort_by");
        } else {
            sortBy = "registered";
        }

        String page = req.getParameter("pg");
        if (page == null || page.equals("")) {
            req.setAttribute("pg", 1);
        } else {
            req.setAttribute("pg", Integer.parseInt(page));
        }
        if (req.getParameter("account_number") != null) {
            account_number = Long.parseLong(req.getParameter("account_number"));
            log.info("payments list requested for account" + account_number);
//            payments = transactionService.findAllPaymentsByAccountNumberSorted(account_number, sortBy, order);
            req.setAttribute("account_number", account_number);
        } else {
            if (role == Role.ADMIN && req.getParameter("user_id") == null) {
                log.info("payments list requested by ADMIN");
                user_id = -1L;
            } else {
                user_id = Long.parseLong(req.getParameter("user_id"));
                log.info("payments list requested for user with id= {}", user_id);
            }
//            payments = transactionService.findAllPaymentsByUserIdSorted(user_id, sortBy, order);
            req.setAttribute("user_id", user_id);
        }
//        int pgMax = 1 + payments.size()/10;
//        req.setAttribute("pg_max", pgMax);
//        req.setAttribute("payments", payments);
        req.setAttribute("payment_sort_by", sortBy);
        req.setAttribute("order", order);
        return "/jsp/payments_list.jsp";
    }
}
