package org.anatkor.controllers.command;

import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class AccountCommand implements Command {
    private static final Logger log = LogManager.getLogger(AccountCommand.class);
    private AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        if (action != null && "new".equals(action)) {
            Long user_id = (Long) session.getAttribute("user_auth_id");
            log.info("request for a new account by user id={}", user_id);
            String accountName = req.getParameter("account_name");
            String currency = req.getParameter("currency");

            if (accountName != null && !"".equals(accountName)) {
                if (accountService.newAccount(accountName, currency, user_id)) {
                    return "redirect:/wallet/accounts?message=Account is opened and is waiting for administrator unblock";
                } else return "redirect:/wallet?warn=Account is not opened";
            }
        }

        if (action != null && "refill".equals(action)) {
        }

        if (action != null && "active".equals(action)) {
            Boolean accountActive = Boolean.parseBoolean(req.getParameter("is_active"));
            Long account_id = Long.parseLong(req.getParameter("id_to_do"));
            if (accountService.updateAccountActiveById (account_id, accountActive)) {
                return "redirect:admin?message=Account is updated";
            } else return "redirect:admin?warn=Account is not updated";
            }

        if (req.getParameter("id") != null) {
            Long account_id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("account", accountService.findById(account_id));
            return "/jsp/account.jsp";
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
