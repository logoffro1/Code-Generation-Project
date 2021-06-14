package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.Account;
import io.swagger.model.ModifyTransactionDTO;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService
{

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(Integer offset, Integer limit)
    {
        if (offset == null || offset < 0)
            throw new ApiRequestException("Offset can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        if (limit == null || limit < 0)
            throw new ApiRequestException("Limit can't be lower than 0 or NULL.", HttpStatus.BAD_REQUEST);

        Pageable pageable = PageRequest.of(offset, limit);
        return transactionRepository.findAll(pageable).getContent();
    }

    public Transaction getTransactionById(long id)
    {
        if (!transactionRepository.findById(id).isPresent())
            throw new ApiRequestException("Transaction with the specified ID not found.", HttpStatus.BAD_REQUEST);

        return transactionRepository.findById(id).get();
    }

    public void createTransaction(Transaction transaction)
    {
        User senderUser = transaction.getSenderAccount().getUser(); //store sender
        User receiverUser = transaction.getReceiverAccount().getUser();
        if (senderUser == null)
            throw new ApiRequestException("Could not retrieve sender user!", HttpStatus.BAD_REQUEST);

        if (receiverUser == null)
            throw new ApiRequestException("Could not retrieve receiver user", HttpStatus.BAD_REQUEST);
        //if the amount is less than 0 or it's more than the limit
        if (transaction.getAmount() <= 0 || transaction.getAmount() > transaction.getAmountLimit())
            throw new ApiRequestException("Invalid amount!", HttpStatus.BAD_REQUEST);

        //make sure the amount is less than the limit
        if (senderUser.getTransactionLimit() < transaction.getAmount())
            throw new ApiRequestException("Amount is higher than the limit!", HttpStatus.BAD_REQUEST);

        //check so the user doesn't exceed the daily limit amount
        if (senderUser.getCurrentTransactionsAmount() + transaction.getAmount() > senderUser.getDayLimit())
            throw new ApiRequestException("Daily limit amount exceeded!", HttpStatus.BAD_REQUEST);

        //increase the user's  current daily transactions amount
        senderUser.setCurrentTransactionsAmount(senderUser.getCurrentTransactionsAmount() + transaction.getAmount());
        //get balance, subtract transaction amount, if that is less than absolute limit, return null (also convert a bunch of double to BigDecimal)
        if (transaction.getSenderAccount().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())).compareTo(transaction.getSenderAccount().getAbsoluteLimit()) < 0)
            throw new ApiRequestException("You can't have that little money in your account!", HttpStatus.BAD_REQUEST);

        if (transaction.getSenderAccount().getStatus() == Account.StatusEnum.CLOSED)
            throw new ApiRequestException("Account cannot be a CLOSED account.", HttpStatus.BAD_REQUEST);

        //One cannot directly transfer from a savings account to an account that is not of the same customer
        //One cannot directly transfer to a savings account from an account that is not from the same customer
        if (transaction.getSenderAccount().getType() == Account.TypeEnum.SAVINGS
                || transaction.getReceiverAccount().getType() == Account.TypeEnum.SAVINGS)
            if (senderUser != receiverUser)
                throw new ApiRequestException("Invalid request", HttpStatus.BAD_REQUEST);


        //transfer money
        sendMoney(transaction.getSenderAccount(), transaction.getReceiverAccount(), transaction.getAmount());

        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactionById(long id)
    {

        if (!transactionRepository.findById(id).isPresent())
            throw new ApiRequestException("Transaction with the specified ID not found.", HttpStatus.BAD_REQUEST);
        Transaction transaction = getTransactionById(id);

        //send the money  back
        sendMoney(transaction.getReceiverAccount(), transaction.getSenderAccount(), transaction.getAmount());
        transactionRepository.deleteById(id);
    }

    @Override
    public void deleteTransaction(Transaction transaction)
    {
        if (transaction == null)
            throw new ApiRequestException("Transaction cannot be NULL.", HttpStatus.BAD_REQUEST);

        //send the money back
        sendMoney(transaction.getReceiverAccount(), transaction.getSenderAccount(), transaction.getAmount());
        transactionRepository.delete(transaction);
    }

    @Override
    public void updateTransaction(Transaction oldTransaction, ModifyTransactionDTO newTransaction)
    {

        if (oldTransaction == null)
            throw new ApiRequestException("Transaction with the specified ID not found.", HttpStatus.BAD_REQUEST);
        if (newTransaction == null)
            throw new ApiRequestException("New transaction cannot be NULL.", HttpStatus.BAD_REQUEST);


        oldTransaction.setAmount(newTransaction.getAmount());
        transactionRepository.save(oldTransaction);
    }

    private void sendMoney(Account senderAccount, Account receiverAccount, Double amount)
    {
        //subtract money from the sender and save
        senderAccount.setBalance(senderAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
        // accountRepository.save(senderAccount);

        //add money to the receiver and save
        receiverAccount.setBalance(receiverAccount.getBalance().add(BigDecimal.valueOf(amount)));
        // accountRepository.save(receiverAccount);
    }
}
