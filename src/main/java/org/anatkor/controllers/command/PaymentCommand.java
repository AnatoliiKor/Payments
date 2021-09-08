package org.anatkor.controllers.command;

import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.User;
import org.anatkor.services.AccountService;
import org.anatkor.services.PaymentService;
import org.anatkor.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

class PaymentCommand implements Command {
    private static final Logger log = LogManager.getLogger(PaymentCommand.class);
    private PaymentService paymentService = new PaymentService();
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
                long receiver = Long.parseLong(req.getParameter("receiver"));
                int amount = (int) (100 * Double.parseDouble(req.getParameter("amount")));
                if (amount <= 0) {
                    return "redirect:wallet";
                }
                String destination = req.getParameter("destination");
                if (amount > account.getBalance()) {
                    return "redirect:wallet/payment?warn=not_enough&receiver=" +
                            receiver + "&amount=" + amount;
                }
/*TODO null user*/
//                User receiverFullName = userService.findUserFullNameByAccounNumber(account.getNumber());
                session.setAttribute("receiver_full_name", userService.findUserFullNameByAccounNumber(receiver));
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
