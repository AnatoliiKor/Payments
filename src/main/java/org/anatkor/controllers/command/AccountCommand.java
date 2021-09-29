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

        if (action != null) {
            if ("new".equals(action)) {
                Long user_id = (Long) session.getAttribute("user_auth_id");
                log.info("request for a new account by user id={}", user_id);
                String accountName = req.getParameter("account_name");
                String currency = req.getParameter("currency");
                if (accountName != null && !"".equals(accountName)) {
                    if (accountService.newAccount(accountName, currency, user_id)) {
                        return "redirect:/wallet/accounts?message=account_opened&user_id=" + user_id;
                    } else return "redirect:/wallet?warn=account_not_opened";
                }
            }

            if ("refill".equals(action)) {
                Long account_id = Long.parseLong(req.getParameter("id_to_do"));
                try {
                    int amount = (int) (100 * Double.parseDouble(req.getParameter("amount")));
                    log.info("request to refill account id={} with amount={}", account_id, amount);
                    if (amount > 0 && accountService.updateAccountBalanceById(account_id, amount)) {
                        return "redirect:account?message=balance_refilled&id=" + account_id;
                    } else return "redirect:account?warn=balance_not_refilled&id=" + account_id;
                } catch (NumberFormatException e) {
                    log.debug("Wrong amount format {}", e.getMessage());
                }


            }

            if ("active".equals(action)) {
                Boolean accountActive = Boolean.parseBoolean(req.getParameter("is_active"));
                Long account_id = Long.parseLong(req.getParameter("id_to_do"));
                if (accountService.updateAccountActiveById(account_id, accountActive)) {
                    return "redirect:admin?message=updated";
                } else return "redirect:admin?warn=not_updated";
            }

            if ("action".equals(action)) {
                int accountAction = Integer.parseInt(req.getParameter("to_do"));
                Long account_id = Long.parseLong(req.getParameter("id_to_do"));
                if (accountService.updateAccountActionById(account_id, accountAction)) {
                    return "redirect:account?message=request_sent&id=" + account_id;
                } else return "redirect:account?warn=request_not_sent&id=" + account_id;
            }
        }

        if (req.getParameter("id") != null) {
            Long account_id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("account", accountService.findById(account_id));
            return "/jsp/account.jsp";
        }

        return "redirect:/wallet";
    }
}
