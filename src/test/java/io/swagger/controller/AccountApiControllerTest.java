package io.swagger.controller;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.repository.UserRepository;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.IbanGeneratorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private IbanGeneratorService ibanGenerator;

    private Account account;

    @MockBean
    private AccountDTO postAccount;

    @MockBean

    private ModifyAccountDTO modifyAccountDTO;


    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
   private User mockUser;

    @BeforeEach
    public void init() {
        User mockUser = new User("firstName","lastName","email","password","090078601", User.RoleEnum.EMPLOYEE);
        mockUser.setId(1003);

        modifyAccountDTO= new ModifyAccountDTO(Account.TypeEnum.CURRENT);
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
    public void whenCreateAccountShouldReturnCreated() throws Exception{
        this.postAccount.setUser(mockUser);
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

    @Test
    public void updateAccountShoukdReturnOk() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        accountService.createAccount(this.account);
        this.mvc.perform(put("/accounts/"+this.account.getIBAN()).contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(this.modifyAccountDTO))).andExpect(status().isOk());
    }

    @Test
    public void deleteAccountShouldReturnOk() throws Exception
    {
        accountService.createAccount(this.account);
        this.mvc.perform(delete("/accounts/"+this.account.getIBAN())).andExpect(status().isOk());
    }

}
