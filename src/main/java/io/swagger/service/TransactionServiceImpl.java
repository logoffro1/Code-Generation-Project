package io.swagger.service;

import com.sun.xml.bind.v2.model.core.ID;
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

    public Transaction getTransactionById(int id)
    {
        return transactionRepository.findById(id).get();
    }

    public void createTransaction(Transaction transaction)
    {
        transactionRepository.save(transaction);
    }
}
