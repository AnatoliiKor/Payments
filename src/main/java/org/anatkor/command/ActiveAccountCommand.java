package org.anatkor.command;

import org.anatkor.services.AccountService;
import javax.servlet.http.HttpServletRequest;

class ActiveAccountCommand implements Command {
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
        String action = req.getParameter("action");
        if ("active".equals(action)) {
            return changeAccountIsActive(req);
        }
        if ("action".equals(action)) {
            return accountToDoRequest(req);
        }
        return "redirect:/wallet";
    }

    private String changeAccountIsActive(HttpServletRequest req) {
        Boolean accountIsActive = Boolean.parseBoolean(req.getParameter("is_active"));
        Long account_id = Long.parseLong(req.getParameter("id_to_do"));
        if (accountService.updateAccountActiveById(account_id, accountIsActive)) {
            return "redirect:admin/accounts?message=updated";
        } else return "redirect:admin/accounts?warn=not_updated";
    }

    private String accountToDoRequest(HttpServletRequest req) {
        int accountAction = Integer.parseInt(req.getParameter("to_do"));
        Long account_id = Long.parseLong(req.getParameter("id_to_do"));
        if (accountService.updateAccountActionById(account_id, accountAction)) {
            return "redirect:account?message=request_sent&id=" + account_id;
        } else return "redirect:account?warn=request_not_sent&id=" + account_id;
    }
}
