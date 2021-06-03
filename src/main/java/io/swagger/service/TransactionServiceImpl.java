package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService
{

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(int offset, int limit)
    {
        Pageable pageable= PageRequest.of(offset,limit);
        return  transactionRepository.findAll(pageable).getContent();
    }

    public Transaction getTransactionById(long id)
    {
        return transactionRepository.findById(id).get();
    }

    public Transaction createTransaction(Transaction transaction)
    {
        if(transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAmountLimit())
            return null;
      //  transaction.getSenderAccount().getUser().
     //   transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public Transaction deleteTransactionById(long id)
    {

        Transaction transaction = transactionRepository.findById(id).get();
        transactionRepository.deleteById(id);
        return transaction;
    }

    @Override
    public Transaction deleteTransaction(Transaction transaction)
    {
        transactionRepository.delete(transaction);
        return transaction;
    }

    @Override
    public Transaction updateTransaction(long transactionId, Transaction newTransaction)
    {
        Transaction oldTransaction = transactionRepository.findById(transactionId).get();
        oldTransaction.setAmount(newTransaction.getAmount());
        transactionRepository.save(oldTransaction);
        return oldTransaction;
    }
}
