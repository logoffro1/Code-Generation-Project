package io.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration(classes = {TransactionService.class})
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;

    @MockBean
    private TransactionDTO transactionDTO;

    @MockBean
    private ModifyTransactionDTO modifyTransactionDTO;


    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init()
    {
        User mockUser = new User("firstName", "lastName", "email", "password", "090078601", User.RoleEnum.ROLE_EMPLOYEE);

        modifyTransactionDTO = new ModifyTransactionDTO(3000.00);
        Account senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(5000));
        Account receiverAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(5000));
        transaction = new Transaction(senderAccount, receiverAccount, 1000.00, "EUR");

    }
    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    void getTransactionsShouldReturnAJsonArray() throws Exception
    {
        given(transactionService.getAllTransactions(0, 5)).willReturn(List.of(transaction));
        this.mvc.perform(
                get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isOk()).andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].amount",is(transaction.getAmount())));
    }
}
