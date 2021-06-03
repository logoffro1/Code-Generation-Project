package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService
{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    public List<Transaction> getAllTransactions(int offset, int limit)
    {
        Pageable pageable = PageRequest.of(offset, limit);
        return transactionRepository.findAll(pageable).getContent();
    }

    public Transaction getTransactionById(long id)
    {
        return transactionRepository.findById(id).get();
    }

    public Transaction createTransaction(Transaction transaction)
    {
        User senderUser = transaction.getSenderAccount().getUser();
        if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAmountLimit()) return null;
        if (senderUser.getTransactionLimit() > transaction.getAmount()) return null;
        if (senderUser.getCurrentTransactionsAmount() + transaction.getAmount() > senderUser.getDayLimit()) return null;

        senderUser.setCurrentTransactionsAmount(senderUser.getCurrentTransactionsAmount() + transaction.getAmount());
        //get balance, subtract transaction amount, if that is less than absolute limit, return null (also convert a bunch of double to BigDecimal)
        if (transaction.getSenderAccount().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())).compareTo(BigDecimal.valueOf(transaction.getSenderAccount().getAbsoluteLimit())) < 0)
            return null;

        transactionRepository.save(transaction);

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
