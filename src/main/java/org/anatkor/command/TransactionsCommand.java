package org.anatkor.command;

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
        List<Transaction> transactions = null;
        HttpSession session = req.getSession();
        Role role = Role.valueOf((String) session.getAttribute("role"));
        accountType = req.getParameter("account_type");

        if (req.getParameter("order") != null) {
            order = req.getParameter("order");
        } else {
            order = "DESC";
        }
        if (req.getParameter("sort_by") != null) {
            sortBy = req.getParameter("sort_by");
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
        }

        if (req.getParameter("user_id") != null) {
            user_id = Long.parseLong(req.getParameter("user_id"));
            log.info("transactions list requested for user with id= {}", user_id);
            transactions = transactionService.findAllTransactionsByUserIdSorted(user_id, sortBy, order, accountType);
            req.setAttribute("user_id", user_id);
        }

        if (role == Role.ADMIN && req.getParameter("user_id") == null && req.getParameter("account_number") == null) {
            log.info("transactions list requested by ADMIN");
            transactions = transactionService.findAllTransactionsSorted(sortBy, order);
        }

        if (transactions != null) {
            int pgMax = 1 + transactions.size() / 10;
            req.setAttribute("pg_max", pgMax);
            req.setAttribute("transactions", transactions);
            req.setAttribute("account_type", accountType);
            req.setAttribute("order", order);
            req.setAttribute("sort_by", sortBy);
        }
        return "/jsp/transactions_list.jsp";
    }
}

