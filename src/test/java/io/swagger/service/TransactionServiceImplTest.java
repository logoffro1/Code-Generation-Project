package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest
{

    @Mock
    private TransactionRepository transactionRepository;
    private TransactionServiceImpl transactionService;
    private AutoCloseable autoCloseable;


    @Autowired
    @InjectMocks
    private TransactionServiceImpl transactionService1;
    private List<Transaction> transactionsList;

    private User mockUser;
    private Transaction transaction;

    @BeforeEach
    public void init()
    {

        transactionsList = new ArrayList<>();
        mockUser = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.CUSTOMER);
        Account senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        Account receivingAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        transaction = new Transaction(senderAccount, receivingAccount, 5000.00, "EUR");
    }

    @AfterEach
    public void tearDown()
    {
        mockUser = null;
        transactionsList = null ;
    }

    @Test
    void getAllTransactions()
    {
    }

    @Test
    void getTransactionById()
    {
    }

    @Test
    void createTransactionSenderUserShouldNotBeNull()
    {
        transaction.getSenderAccount().setUser(null);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService1.createTransaction(transaction));
        Assertions.assertEquals("Could not retrieve sender user!", exception.getMessage());

    }

    void createTransactionReceiverUserShouldNotBeNull()
    {
        transaction.getReceiverAccount().setUser(null);
        Exception exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Could not retrieve receiver user!", exception.getMessage());

    }

    @Test
    void deleteTransactionById()
    {
    }

    @Test
    void deleteTransaction()
    {
    }

    @Test
    void updateTransaction()
    {
    }

/*

    @Test
    public void CanAddCreatedUser(){

        transactionRepository.save(mockUser);
        when(transactionRepository.findAll()).thenReturn(transactionsList);
        List<User> userList1 = transactionService.getAllUsers();
        assertEquals(userList1, transactionsList);
    }

    @Test
    public void CanGetAllUsers(){
        transactionService.getAllUsers();
        verify(transactionRepository.findAll());
    }
*/


}