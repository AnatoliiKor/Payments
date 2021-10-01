package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.enums.Currency;
import org.anatkor.services.AccountService;
import org.anatkor.services.TransactionService;
import org.anatkor.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class PreparePaymentCommand implements Command {
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (req.getParameter("account_id") != null
                && req.getParameter(Constant.RECEIVER) != null
                && req.getParameter(Constant.AMOUNT) != null
        ) {
            Account account = accountService.findById(Long.parseLong(req.getParameter("account_id")));
            long receiver = Long.parseLong(req.getParameter(Constant.RECEIVER));
            String destination = req.getParameter("destination");
            int amount = (int) (100 * Double.parseDouble(req.getParameter(Constant.AMOUNT)));
            if (account == null) {
                return "redirect:/payment";
            }
            if (amount <= 0) {
                return "redirect:wallet";
            }
            if (amount > account.getBalance()) {
                return "redirect:wallet/payment?warn=not_enough&receiver=" +
                        receiver + "&amount=" + amount;
            }
            Currency currency;
            try {
                currency = accountService.findCurrencyByAccountNumber(receiver);
            } catch (DBException e) {
                return "redirect:wallet/payment?warn=account_not_found";
            }
            if (!account.getCurrency().equals(currency)) {
                return "redirect:wallet/payment?warn=not_currency&message=" + currency.name() + "&receiver=" +
                        receiver + "&amount=" + amount;
            }
            session.setAttribute("payment", transactionService.getPayment(account, receiver, destination, amount, currency));
        }
        return "/jsp/make_payment.jsp";
    }


}
