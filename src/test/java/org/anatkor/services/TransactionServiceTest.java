package org.anatkor.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionServiceTest {
    private final TransactionService transactionService = new TransactionService();


    @Test
    public void makeTransaction() {
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