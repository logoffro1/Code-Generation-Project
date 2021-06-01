package io.swagger.service;

import io.swagger.model.Transaction;

import java.util.List;

public interface TransactionService
{
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(long id);
    void createTransaction(Transaction transaction);
    void deleteTransactionById(long id);
    void deleteAllTransactions(List<Transaction> transactions);
    void deleteAllTransactions();

}
