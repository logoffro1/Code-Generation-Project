package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService
{

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions()
    {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public Transaction getTransactionById(long id)
    {
        return transactionRepository.findById(id).get();
    }

    public void createTransaction(Transaction transaction)
    {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactionById(long id)
    {
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteTransaction(Transaction transaction)
    {
        transactionRepository.delete(transaction);
    }
}
