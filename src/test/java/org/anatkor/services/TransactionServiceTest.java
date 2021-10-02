package org.anatkor.services;

import org.anatkor.model.Account;
import org.anatkor.model.Payment;
import org.anatkor.model.Transaction;
import org.anatkor.model.enums.Currency;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionServiceTest {
    private final TransactionService transactionService = new TransactionService();

    @Test
    public void checkAccount_nullAccount_returnsRedirectPayment() {
        Account account = null;
        Assert.assertEquals("redirect:/payment", transactionService.checkAccount(account, 0));
    }

    @Test
    public void checkAccount_negativeAmount_returnsRedirectPayment() {
        Account account = new Account();
        Assert.assertEquals("redirect:/payment", transactionService.checkAccount(account, -10));
    }


    @Test
    public void checkAccount_NotEnoughBalance_returnsRedirectPayment() {
        Account account = new Account();
        account.setBalance(500);
        Assert.assertTrue(transactionService.checkAccount(account, 1000).contains("not_enough"));
    }

    @Test
    public void checkAccount_returnsChecked() {
        Account account = new Account();
        account.setBalance(500);
        Assert.assertEquals("checked", transactionService.checkAccount(account, 100));
    }

    @Test
    public void checkReceiverForActiveAndCurrency_accountNotFound() {
        String redirect = transactionService.checkReceiverForActiveAndCurrency(1L, Currency.EURO);
        Assert.assertTrue(redirect.contains("account_not_found"));
    }

    @Test
    public void checkReceiverForActiveAndCurrency_wrongCurrency() {
        String redirect = transactionService.checkReceiverForActiveAndCurrency(10000000026L, Currency.EURO);
        Assert.assertTrue(redirect.contains("not_currency"));
    }

    @Test
    public void checkReceiverForActiveAndCurrency_returnsChecked() {
        String redirect = transactionService.checkReceiverForActiveAndCurrency(10000000026L, Currency.UAH);
        Assert.assertEquals("checked", redirect);
    }

    @Test
    public void getPayment() {
        Account account = new Account();
        long receiver = 10000000010L;
        account.setNumber(10000000026L);
        account.setAccountName("name");
        account.setCurrency(Currency.UAH);
        Payment payment = transactionService.getPayment(account, receiver, "-", 100);
        assertEquals(100, payment.getAmount());
        assertEquals(receiver, payment.getReceiver());
    }

    @Test
    public void findAllTransactionsByUserIdSorted() {
        assertNotNull(transactionService.findAllTransactionsByUserIdSorted(42L, "amount", "ASC", "receiver"));
    }

    @Test
    public void findAllTransactionsByAccountNumberSorted() {
        assertNotNull(transactionService.findAllTransactionsByAccountNumberSorted(10000000026L, "amount", "ASC", "receiver"));

    }

    @Test
    public void findAllTransactionsSorted() {
        assertTrue(transactionService.findAllTransactionsSorted("amount", "ASC").size() > 0);
    }
}