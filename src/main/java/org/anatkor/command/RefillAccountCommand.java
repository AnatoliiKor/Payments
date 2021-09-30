package org.anatkor.command;

import org.anatkor.services.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

class RefillAccountCommand implements Command {
    private static final Logger log = LogManager.getLogger(RefillAccountCommand.class);
    private final AccountService accountService = new AccountService();

    @Override
    public String execute(HttpServletRequest req) {
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
        return "redirect:/wallet";
    }
}
