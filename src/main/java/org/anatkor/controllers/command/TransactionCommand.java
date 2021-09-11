//package org.anatkor.controllers.command;
//
//
//import org.anatkor.services.AccountService;
//import org.anatkor.services.TransactionService;
//import org.anatkor.services.UserService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//class TransactionCommand implements Command {
//    private static final Logger log = LogManager.getLogger(TransactionCommand.class);
//    private TransactionService transactionService = new TransactionService();
//    private AccountService accountService = new AccountService();
//    private UserService userService = new UserService();
//
//    @Override
//    public String execute(HttpServletRequest req) {
//        String type = req.getParameter("type");
////        TODO shows transaction's details
//        long transactionId = Long.parseLong(req.getParameter("transaction_id"));
//        return "/jsp/make_payment.jsp";
//    }
//}
