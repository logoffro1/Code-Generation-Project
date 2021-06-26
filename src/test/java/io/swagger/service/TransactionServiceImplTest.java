package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.*;
import io.swagger.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private Account senderAccount;

    User mockUser;

    @BeforeEach
    public void init() {
        mockUser = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.ROLE_CUSTOMER);
        senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(5000));
        Account receiverAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        transaction = new Transaction(senderAccount, receiverAccount, 5000.00, "EUR");
    }

    @Test
    void getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>(List.of(transaction));

        lenient().when(transactionRepository.save(transaction)).thenReturn(transaction);

        Page<Transaction> transactionListPaged = new PageImpl<>(transactions);
        given(transactionRepository.findAll(PageRequest.of(0, 5))).willReturn(transactionListPaged);
        List<Transaction> expected = transactionService.getAllTransactions(0, 5);


        assertEquals(expected, transactions);
    }

/*    @Test
    void getAllTransactionsOffsetShouldNotBeValid() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.getAllTransactions(null, 5));
        Assertions.assertEquals("Offset can't be lower than 0 or NULL.", 0);
    }

    @Test
    void getAllTransactionsOffsetShouldNotBeLessThanZero() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.getAllTransactions(-1, 5));
        Assertions.assertEquals("Offset can't be lower than 0 or NULL.", exception.getMessage());
    }*/

/*    @Test
    void getAllTransactionsLimitShouldNotBeValid() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.getAllTransactions(0, null));
        Assertions.assertEquals("Limit can't be lower than 1 or NULL.", exception.getMessage());
    }

    @Test
    void getAllTransactionsLimitShouldNotBeLessThanZero() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.getAllTransactions(0, -1));
        Assertions.assertEquals("Limit can't be lower than 1 or NULL.", exception.getMessage());
    }*/

    @Test
    void getTransactionById() {

        AuthorizedUser user = new AuthorizedUser(mockUser);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        lenient().when(transactionRepository.findById(transaction.getTransactionId())).thenReturn(java.util.Optional.ofNullable(transaction));

        Transaction expected = transactionService.getTransactionById(transaction.getTransactionId());
        assertEquals(expected, transaction);

        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.getTransactionById(2));
        Assertions.assertEquals("Transaction with the specified ID not found.", exception.getMessage());
    }

    @Test
    void createTransactionSenderUserShouldNotBeNull() {
        transaction.getSenderAccount().setUser(null);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Could not retrieve sender user!", exception.getMessage());
    }

    @Test
    void createTransactionReceiverUserShouldNotBeNull() {
        transaction.getReceiverAccount().setUser(null);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Could not retrieve receiver user!", exception.getMessage());
    }

    @Test
    void createTransactionAmountShouldBeValid() {
        transaction.setAmount(-1.00);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Invalid amount!", exception.getMessage());

        transaction.setAmount(transaction.getAmountLimit() + 1000.00);

        exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Invalid amount!", exception.getMessage());
    }

    @Test
    void createTransactionShouldNotLeaveAccountOnNegativeBalance() {
        transaction.setAmount(6000.00);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("You can't have that little money in your account!", exception.getMessage());
    }

    @Test
    void createTransactionAccountShouldNotBeClosed() {
        senderAccount.setStatus(Account.StatusEnum.CLOSED);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Account cannot be a CLOSED account.", exception.getMessage());
    }


    @Test
    void createTransactionUserTransactionLimitShouldNotBeMoreThanAmount() {
        transaction.getSenderAccount().getUser().setTransactionLimit(transaction.getAmount() - 1);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Amount is higher than the limit!", exception.getMessage());
    }

    @Test
    void createTransactionDailyLimitAmountShouldNotBeExceeded(){
        transaction.getSenderAccount().getUser().setDayLimit(1000.00);
        transaction.getSenderAccount().getUser().setCurrentTransactionsAmount(transaction.getAmount() + transaction.getSenderAccount().getUser().getDayLimit());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.createTransaction(transaction));
        Assertions.assertEquals("Daily limit amount exceeded!", exception.getMessage());
    }
    @Test
    void deleteTransactionById() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.deleteTransactionById(-1));
        Assertions.assertEquals("Transaction with the specified ID not found.", exception.getMessage());
    }

/*    @Test
    void deleteTransaction() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> transactionService.deleteTransaction(null));
        Assertions.assertEquals("Transaction cannot be NULL.", exception.getMessage());
    }*/


}