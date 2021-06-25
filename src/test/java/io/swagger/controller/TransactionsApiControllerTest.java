package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.repository.TransactionRepository;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.IbanGeneratorService;
import io.swagger.service.TransactionServiceImpl;
import io.swagger.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    private User mockUser;

    @Autowired
    private IbanGeneratorService ibanGenerator;

    private Account senderAccount;
    private Account receiverAccount;


    private Transaction transaction;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        User user = new User("Bank", "NL", "BankNl@gmail.com", "BankPassword", "+31 06 929281802", 1000000.00, 1000.00, User.RoleEnum.ROLE_EMPLOYEE);
        user.setId(1003);
        userService.createUser(user);
        this.senderAccount = new Account(ibanGenerator.generateIban(), BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
        this.receiverAccount = new Account(ibanGenerator.generateIban(), BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));

        accountService.createAccount(senderAccount);
        accountService.createAccount(receiverAccount);

        this.transaction = new Transaction(senderAccount, receiverAccount, 1.00, "EUR");

    }

    @Test
    void getAllTransactions() {
    }

    @Test
    void createTransaction() {
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void deleteTransactionByid() throws Exception {

        when(mockUser.getTransactionLimit()).thenReturn(10000.00);
        when(mockUser.getDayLimit()).thenReturn(10000.00);
        transactionService.createTransaction(this.transaction);

        this.mvc.perform(delete("/transactions/" + this.transaction.getTransactionId())).andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void getTransactionById() throws Exception {

       /* when(mockUser.getTransactionLimit()).thenReturn(10000.00);
        when(mockUser.getDayLimit()).thenReturn(10000.00);*/

        transactionService.createTransaction(transaction);

        this.mvc.perform((get("/transactions/" + transaction.getTransactionId()))
        ).andExpect(status().isOk());
    }
}