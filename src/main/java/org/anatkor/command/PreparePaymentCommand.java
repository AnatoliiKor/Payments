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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class PreparePaymentCommand implements Command {
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();
    private static final Logger log = LogManager.getLogger(RefillAccountCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (req.getParameter(Constant.ACCOUNT_ID) != null
                && req.getParameter(Constant.RECEIVER) != null
                && req.getParameter(Constant.AMOUNT) != null
        ) {
            String redirect;
            long accountNumber;
            int amount;
            long receiver;
            try {
                accountNumber = Long.parseLong(req.getParameter(Constant.ACCOUNT_ID));
                amount = (int) (100 * Double.parseDouble(req.getParameter(Constant.AMOUNT)));
                receiver = Long.parseLong(req.getParameter(Constant.RECEIVER));
            } catch (NumberFormatException e) {
                log.debug("Wrong format {}", e.getMessage());
                return "redirect:/payment?warn=not_data";
            }
            Account account = accountService.findById(accountNumber);
            redirect = transactionService.checkAccount(account, amount);
            if (!Constant.CHECKED.equals(redirect)) {
                return redirect;
            }
            redirect = transactionService.checkReceiverForActiveAndCurrency(receiver, account.getCurrency());
            if (!Constant.CHECKED.equals(redirect)) {
                return redirect;
            }
            String destination = Util.getRequestParamOrDefault(req, "destination", "-");
            session.setAttribute(Constant.PAYMENT, transactionService.getPayment(account, receiver, destination, amount));
        }
        return "/jsp/make_payment.jsp";
    }
}
