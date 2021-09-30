package org.anatkor.command;

import org.anatkor.constants.Constant;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.User;
import org.anatkor.services.TransactionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class ConfirmPaymentCommand implements Command {
    private final TransactionService transactionService = new TransactionService();

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(Constant.USER_AUTH);
        long userId = user.getId();
        Transaction payment = (Payment) session.getAttribute(Constant.PAYMENT);
        if (payment != null && transactionService.makeTransaction(payment)) {
            session.removeAttribute("payment");
            return "redirect:transactions?message=payment_success&account_type=payer&user_id=" + userId;
        } else {
            session.removeAttribute("payment");
            return "redirect:wallet?warn=payment_fail";
        }
    }
}
