package org.anatkor.controllers.command;

import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class AccountCommand implements Command {
    private static final Logger log = LogManager.getLogger(AccountCommand.class);
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        String action = req.getParameter("action");
        if (action != null && "new".equals(action)) {
            log.info("request for a new account");
            String accountName = req.getParameter("account_name");
            String currency = req.getParameter("currency");
            Long user_id = Long.parseLong(req.getParameter("id"));
            if (accountName != null && !"".equals(accountName)) {
                if (accountService.newAccount(accountName, currency, user_id)) {
                    return "redirect:/wallet/accounts?message=Account is opened";
                } else return "redirect:/wallet?warn=Account is not opened";
            }
        }
        return "redirect:/wallet";
    }





//
//
//
//    String name = req.getParameter("name");
//    String middleName = req.getParameter("middle_name");
//        if(middleName==null)
//
//    {
//        middleName = "";
//    }
//
//    String password = req.getParameter("password");
//    String email = req.getParameter("email");
//    long phoneNumber = Long.parseLong("38" + req.getParameter("phone_number"));
//
//        try
//
//    {
//        if (userService.addUser(lastName, name, middleName, password, email, phoneNumber)) {
//            req.setAttribute("message", "Client \"" + lastName + " " + name + " " + middleName + "\" registered successfully. Sign in");
//            return "/login";
//        }
//        req.setAttribute("message", "Client is not registered. Try again");
//        return "/jsp/registration.jsp";
//    } catch(
//    DBException e)
//
//    {
//        req.setAttribute("warn", e.getMessage());
//        req.setAttribute("last_name", lastName);
//        req.setAttribute("name", name);
//        req.setAttribute("middle_name", middleName);
//        req.setAttribute("email", email);
//        return "/jsp/registration.jsp";
//    }
//}
}
