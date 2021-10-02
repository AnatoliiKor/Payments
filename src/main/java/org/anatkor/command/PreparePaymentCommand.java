package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.enums.Currency;
import org.anatkor.services.AccountService;
import org.anatkor.services.TransactionService;
import org.anatkor.services.UserService;
import org.anatkor.utils.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class PreparePaymentCommand implements Command {
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (req.getParameter(Constant.ACCOUNT_ID) != null
                && req.getParameter(Constant.RECEIVER) != null
                && req.getParameter(Constant.AMOUNT) != null
        ) {
            String redirect;
            Account account = accountService.findById(Long.parseLong(req.getParameter(Constant.ACCOUNT_ID)));
            int amount = (int) (100 * Double.parseDouble(req.getParameter(Constant.AMOUNT)));
            redirect = transactionService.checkAccount(account, amount);
            if (!"checked".equals(redirect)) {
                return redirect;
            }
            long receiver = Long.parseLong(req.getParameter(Constant.RECEIVER));
            redirect = transactionService.checkReceiverForActiveAndCurrency(receiver, account.getCurrency());
            if (!"checked".equals(redirect)) {
                return redirect;
            }
            String destination = Util.getRequestParamOrDefault(req, "destination", "-");
            session.setAttribute("payment", transactionService.getPayment(account, receiver, destination, amount));
        }
        return "/jsp/make_payment.jsp";
    }
}
