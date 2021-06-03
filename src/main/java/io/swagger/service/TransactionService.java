package io.swagger.service;

import io.swagger.model.Transaction;

import java.util.List;

public interface TransactionService
{
    List<Transaction> getAllTransactions(int offset, int limit);

    Transaction getTransactionById(long id);

    Transaction createTransaction(Transaction transaction);

    Transaction deleteTransactionById(long id);

    Transaction deleteTransaction(Transaction transaction);
    Transaction updateTransaction(long transactionId, Transaction newTransaction);

}
