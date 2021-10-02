package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Role;
import org.anatkor.services.TransactionService;
import org.anatkor.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class TransactionsCommand implements Command {
    private static final Logger log = LogManager.getLogger(TransactionsCommand.class);
    private final TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        String accountType;
        HttpSession session = req.getSession();
        Role role = Role.valueOf((String) session.getAttribute("role"));
        accountType = req.getParameter("account_type");
        String order = Util.getRequestParamOrDefault(req, Constant.ORDER, Constant.DESC);
        String sortBy = Util.getRequestParamOrDefault(req, Constant.SORT_BY, Constant.REGISTERED);
        Util.requestGetAndSetPage(req);
        List<Transaction> transactions = getTransactions(req, accountType, role, order, sortBy);
        if (transactions != null) {
            fillRequest(req, accountType, order, sortBy, transactions);
        }
        return "/jsp/transactions_list.jsp";
    }

    private List<Transaction> getTransactions(HttpServletRequest req, String accountType, Role role, String order, String sortBy) {
        long accountNumber;
        long user_id;
        List<Transaction> transactions = null;

        if (req.getParameter(Constant.ACCOUNT_NUMBER) != null) {
            accountNumber = Long.parseLong(req.getParameter(Constant.ACCOUNT_NUMBER));
            transactions = transactionService.findAllTransactionsByAccountNumberSorted(accountNumber, sortBy, order, accountType);
            req.setAttribute(Constant.ACCOUNT_NUMBER, accountNumber);
        }

        if (req.getParameter(Constant.USER_ID) != null) {
            user_id = Long.parseLong(req.getParameter(Constant.USER_ID));
            transactions = transactionService.findAllTransactionsByUserIdSorted(user_id, sortBy, order, accountType);
            req.setAttribute(Constant.USER_ID, user_id);
        }

        if (role == Role.ADMIN && req.getParameter(Constant.USER_ID) == null && req.getParameter(Constant.ACCOUNT_NUMBER) == null) {
            transactions = transactionService.findAllTransactionsSorted(sortBy, order);
        }
        return transactions;
    }

    private void fillRequest(HttpServletRequest req, String accountType, String order, String sortBy, List<Transaction> transactions) {
        int pgMax = 1 + transactions.size() / 10;
        req.setAttribute("pg_max", pgMax);
        req.setAttribute(Constant.TRANSACTIONS, transactions);
        req.setAttribute("account_type", accountType);
        req.setAttribute(Constant.ORDER, order);
        req.setAttribute(Constant.SORT_BY, sortBy);
    }
}

