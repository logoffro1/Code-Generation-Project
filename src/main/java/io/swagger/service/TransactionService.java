package io.swagger.service;

import io.swagger.model.Transaction;

import java.util.List;

public interface TransactionService
{
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(int id);
    void createTransaction(Transaction transaction);

}
