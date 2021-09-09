package org.anatkor.controllers.command;

import org.anatkor.dao.TransactionDao;
import org.anatkor.exceptions.DBException;
import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.User;
import org.anatkor.model.enums.Currency;
import org.anatkor.services.AccountService;
import org.anatkor.services.TransactionService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class PaymentCommand implements Command {
    private static final Logger log = LogManager.getLogger(PaymentCommand.class);
    private TransactionService transactionService = new TransactionService();
    private AccountService accountService = new AccountService();
    private UserService userService = new UserService();


    @Override
    public String execute(HttpServletRequest req) {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user_auth");
        long userId = user.getId();

        if (action == null) {
            List<Account> accounts = accountService.findAllAccountsByUserId(userId);
            req.setAttribute("accounts", accounts);
            if (req.getParameter("receiver") != null && req.getParameter("amount") != null) {
                req.setAttribute("receiver", req.getParameter("receiver"));
                req.setAttribute("amount", req.getParameter("amount"));
            }
        }
        if (action != null) {
            if ("prepare".equals(action)
                    && req.getParameter("account_id") != null
                    && req.getParameter("receiver") != null
                    && req.getParameter("amount") != null
            ) {
                Account account = accountService.findById(Long.parseLong(req.getParameter("account_id")));
                if (account == null) {
                    return "redirect:payment";
                }
                long receiver = Long.parseLong(req.getParameter("receiver"));
                String destination = req.getParameter("destination");
                int amount = (int) (100 * Double.parseDouble(req.getParameter("amount")));
                if (amount <= 0) {
                    return "redirect:wallet";
                }
                if (amount > account.getBalance()) {
                    return "redirect:wallet/payment?warn=not_enough&receiver=" +
                            receiver + "&amount=" + amount;
                }
                Currency currency;
                try {
                    currency  = accountService.findCurrencyByAccountNumber(receiver);
                } catch (DBException e) {
                    return "redirect:wallet/payment?warn=account_not_found";
                }
                if (!account.getCurrency().equals(currency)) {
                    return "redirect:wallet/payment?warn=not_currency&message=" + currency.name() + "&receiver=" +
                            receiver + "&amount=" + amount;
                }
                Payment payment = new Payment();
                payment.setPayer(account.getNumber());
                payment.setReceiver(receiver);
                payment.setPayerAccountName(account.getAccountName());
                payment.setReceiverFullName(userService.findUserFullNameByAccounNumber(receiver));
                if (destination != null) {
                    payment.setDestination(destination);
                } else {
                    payment.setDestination("-");
                }
                payment.setAmount(amount);
                payment.setCurrency(currency);
                session.setAttribute("payment", payment);
            }

            if ("confirm".equals(action)) {
                Transaction payment = (Payment) session.getAttribute("payment");
                if (payment != null && transactionService.makeTransaction(payment)) {
                    session.removeAttribute("payment");
                    return "redirect:payments?message=payment_success&user_id=" + userId;
                } else {
                    session.removeAttribute("payment");
                    return "redirect:wallet?warn=payment_fail";
                }
            }

            if ("cancel".equals(action)) {
                session.removeAttribute("payment");
                session.removeAttribute("receiver_full_name");
                return "redirect:wallet?message=canceled";
            }
        }
        return "/jsp/make_payment.jsp";
    }
}
