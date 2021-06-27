package io.swagger.service;

import io.swagger.model.ModifyTransactionDTO;
import io.swagger.model.Transaction;

import java.util.List;

public interface TransactionService
{
    List<Transaction> getAllTransactions(Integer offset,Integer limit);

    Transaction getTransactionById(long id);

    void createTransaction(Transaction transaction);

    void deleteTransactionById(long id);

   // void deleteTransaction(Transaction transaction);
  //  void updateTransaction(Transaction oldTransaction, ModifyTransactionDTO newTransaction);

}
