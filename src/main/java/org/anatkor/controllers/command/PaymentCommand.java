package org.anatkor.controllers.command;

import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

class PaymentCommand implements Command {
    private static final Logger log = LogManager.getLogger(PaymentCommand.class);
    private PaymentService paymentService = new PaymentService();
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        if (action == null) {
            User user = (User) session.getAttribute("user_auth");
            long userId = user.getId();
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
                long receiver = Long.parseLong(req.getParameter("receiver"));
                int amount = (int) (100 * Double.parseDouble(req.getParameter("amount")));
                if (amount <= 0) {
                    return "redirect:wallet";
                }
                String destination = req.getParameter("destination");
                if (amount/100 > account.getBalance()) {
                    return "redirect:wallet/payment?warn=not_enough&receiver=" +
                            receiver + "&amount=" + amount;
                }
                Payment payment = new Payment();
                payment.setAccountNumber(account.getNumber());
                payment.setAccountName(account.getAccountName());
                payment.setReceiver(receiver);
                if (destination != null) {
                    payment.setDestination(destination);
                } else {
                    payment.setDestination("-");
                }
                payment.setAmount(amount);
                payment.setCurrency(account.getCurrency());
                session.setAttribute("payment", payment);

            }

            if ("confirm".equals(action)) {
                Payment payment = (Payment) session.getAttribute("payment");
                if (payment != null && paymentService.makePayment(payment)) {
                    session.removeAttribute("payment");
//                    return "redirect:wallet/payments?message=payment_success";
                    return "redirect:wallet?message=payment_success";
                } else {
                    session.removeAttribute("payment");
                    return "redirect:wallet?warn=payment_fail";
                }
            }

            if ("cancel".equals(action)) {
                session.removeAttribute("payment");
                return "redirect:wallet?message=canceled";
            }

//                req.setAttribute("payment", payment);
//

//
//                Long user_id = (Long) session.getAttribute("user_auth_id");
//                log.info("request for a new account by user id={}", user_id);
//                String accountName = req.getParameter("account_name");
//                String currency = req.getParameter("currency");
//                if (accountName != null && !"".equals(accountName)) {
//                    if (accountService.newAccount(accountName, currency, user_id)) {
//                        return "redirect:/wallet/accounts?message=account_opened&user_id=" + user_id;
//                    } else return "redirect:/wallet?warn=account_not_opened";
//                }
//            }
//
//            if ("refill".equals(action)) {
//                Long account_id = Long.parseLong(req.getParameter("id_to_do"));
//                int amount = (int) (100*Double.parseDouble(req.getParameter("amount")));
//                log.info("request to refill account id={} with amount={}", account_id, amount);
//                if (amount>0 && accountService.updateAccountBalanceById(account_id, amount)) {
//                    return "redirect:account?message=balance_refilled&id=" + account_id;
//                } else return "redirect:account?warn=balance_not_refilled&id=" + account_id;
//            }
//        }
//
//        if (req.getParameter("id") != null) {
//            Long account_id = Long.parseLong(req.getParameter("id"));
//            req.setAttribute("account", accountService.findById(account_id));
//            return "/jsp/account.jsp";
//        }
//
//        return "redirect:/wallet";
        }
        return "/jsp/make_payment.jsp";
    }
}
