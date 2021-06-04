package io.swagger.service;

import io.swagger.model.Account;
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
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

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
        User senderUser = transaction.getSenderAccount().getUser(); //store sender
        //if the amount is less than 0 or it's more than the limit
        if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAmountLimit()) return null;

        //make sure the amount is less than the limit
        if (senderUser.getTransactionLimit() < transaction.getAmount()) return null;

        //check so the user doesn't exceed the daily limit amount
        if (senderUser.getCurrentTransactionsAmount() + transaction.getAmount() > senderUser.getDayLimit()) return null;

        //increase the user's  current daily transactions amount
        senderUser.setCurrentTransactionsAmount(senderUser.getCurrentTransactionsAmount() + transaction.getAmount());

        //get balance, subtract transaction amount, if that is less than absolute limit, return null (also convert a bunch of double to BigDecimal)
        if (transaction.getSenderAccount().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())).compareTo(transaction.getSenderAccount().getAbsoluteLimit()) < 0)
            return null;

        sendMoney(transaction.getSenderAccount(), transaction.getReceiverAccount(), transaction.getAmount());

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

    private void sendMoney(Account senderAccount, Account receiverAccount, Double amount)
    {
        //subtract money from the sender and save
        senderAccount.setBalance(senderAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
        accountRepository.save(senderAccount);

        //add money to the receiver and save
        receiverAccount.setBalance(receiverAccount.getBalance().add(BigDecimal.valueOf(amount)));
        accountRepository.save(receiverAccount);
    }
}
