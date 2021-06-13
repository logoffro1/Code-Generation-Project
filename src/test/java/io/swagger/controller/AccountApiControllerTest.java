package io.swagger.controller;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.AccountDTO;
import io.swagger.model.IbanGenerator;
import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.UserService;
import io.swagger.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private AccountServiceImpl accountService;

    @MockBean
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private IbanGenerator ibanGenerator;

    private Account account;

    private AccountDTO postAccount;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        User mockUser= userService.getUserById(1001);
        this.account = new Account(ibanGenerator.generateIban(), BigDecimal.valueOf(20000), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
        this.postAccount= new AccountDTO(BigDecimal.valueOf(20000),mockUser, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000), Account.TypeEnum.CURRENT);
    }

    @Test
    public void getAccountsShouldReturnAJsonArray() throws Exception {
        given(accountService.getAllAccounts(1, 0)).willReturn(List.of(account));
        this.mvc.perform(
                get("/accounts")).andExpect(
                status().isOk());
    }

    @Test
    public void createAccountShouldReturnCreated() throws Exception{

        this.mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateAccountShouldReturnCreated() throws Exception{
        this.mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(this.postAccount))).andExpect((status().isCreated()));
    }


    @Test
   public void getAccountByIban() throws Exception {

        mvc.perform((get("/accounts/" + account.getIBAN()))
        ).andExpect(status().isOk());
    }

    @Test
    public void callingAllAccountsShouldReturnOK() throws Exception {
    /*
    Removed the servlet context /api for testing
     */
        this.mvc.perform(get("/accounts"))
                .andExpect(status().isOk());
    }


}
