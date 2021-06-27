package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionServiceImpl transactionService;
    //if i remove this everything breaks
    @MockBean
    private AccountServiceImpl accountService;

    private Transaction transaction;

    @MockBean
    private TransactionDTO transactionDTO;


    private User mockUser;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockUser = new User("firstName", "lastName", "email", "password", "090078601", User.RoleEnum.ROLE_EMPLOYEE);

        Account senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(5000));
        Account receiverAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(5000));
        transaction = new Transaction(senderAccount, receiverAccount, 1000.00, "EUR");

    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void getTransactionsShouldReturnAJsonArray() throws Exception {
        //return a list of transactions when accessing /transactions
        when(transactionService.getAllTransactions(0, 100)).thenReturn(List.of(transaction));
        this.mvc.perform(
                get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void whenCreateTransactionsShouldReturnCreated() throws Exception {

        this.mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(this.transactionDTO))).andExpect((status().isCreated()));
    }


    @Test
    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMER"})
    public void getTransactionsByIdShouldReturnOk() throws Exception {
        //mock the getByTransaction to return this.transaction
        when(transactionService.getTransactionById(99)).thenReturn(transaction);
        this.mvc.perform(get("/transactions/99").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(username = "employee", roles = {"EMPLOYEE"})
    public void deleteTransactionById() throws Exception {
        //mock the getByTransaction to return this.transaction
        when(transactionService.getTransactionById(99)).thenReturn(transaction);
        this.mvc.perform(delete("/transactions/99").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }


}
