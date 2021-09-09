package org.anatkor.controllers.command;

import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Role;
import org.anatkor.services.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class TransactionsCommand implements Command {
    private static final Logger log = LogManager.getLogger(TransactionsCommand.class);
    private TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        String sortBy;
        String order;
        String accountType;
        long user_id;
        long accountNumber;
        List<Transaction> transactions;
        HttpSession session = req.getSession();
        Role role = Role.valueOf((String) session.getAttribute("role"));

        accountType = req.getParameter("account_type");
        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
            session.setAttribute("order", order);
        } else if (session.getAttribute("order") != null) {
            order = (String) session.getAttribute("order");
        } else {
            order = "DESC";
        }

        if (req.getParameter("transaction_sort_by") != null) {
            sortBy = req.getParameter("transaction_sort_by");
            session.setAttribute("transaction_sort_by", sortBy);
        } else if (session.getAttribute("transaction_sort_by") != null) {
            sortBy = (String) session.getAttribute("transaction_sort_by");
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
            accountNumber = Long.parseLong(req.getParameter("account_number"));
            log.info("payments list requested for account" + accountNumber);
            transactions = transactionService.findAllTransactionsByAccountNumberSorted(accountNumber, sortBy, order, accountType);
            req.setAttribute("account_number", accountNumber);
        } else {
            if (role == Role.ADMIN && req.getParameter("user_id") == null) {
                log.info("transactions list requested by ADMIN");
                user_id = -1L;
            } else {
                user_id = Long.parseLong(req.getParameter("user_id"));
                log.info("transactions list requested for user with id= {}", user_id);
            }
            transactions = transactionService.findAllTransactionsByUserIdSorted(user_id, sortBy, order, accountType);
            req.setAttribute("user_id", user_id);
        }
        int pgMax = 1 + transactions.size()/10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute("transactions", transactions);
        req.setAttribute("transaction_sort_by", sortBy);
        req.setAttribute("order", order);
//        return "/jsp/payments_list.jsp";
        return "/jsp/transactions_list.jsp";
    }
}
