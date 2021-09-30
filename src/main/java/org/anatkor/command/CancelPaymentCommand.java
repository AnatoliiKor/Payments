package org.anatkor.command;

import org.anatkor.constants.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class CancelPaymentCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute(Constant.PAYMENT);
        session.removeAttribute("receiver_full_name");
        return "redirect:wallet?message=canceled";
    }
}
