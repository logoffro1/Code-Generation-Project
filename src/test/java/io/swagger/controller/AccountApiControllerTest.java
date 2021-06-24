package io.swagger.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.IbanGeneratorService;
import io.swagger.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    private CreateAccountDTO postAccount;

    @MockBean

    private ModifyAccountDTO modifyAccountDTO;


    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private User mockUser;


    @BeforeEach
    public void init() {
        User mockUser = new User("firstName","lastName","email","password","090078601", User.RoleEnum.ROLE_EMPLOYEE);
        mockUser.setId(1003);

        modifyAccountDTO= new ModifyAccountDTO(Account.TypeEnum.CURRENT);
        this.account = new Account(ibanGenerator.generateIban(), BigDecimal.valueOf(20000), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
        this.postAccount= new CreateAccountDTO(BigDecimal.valueOf(20000),mockUser.getId(), Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000), Account.TypeEnum.CURRENT);
    }


    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void getAccountsShouldReturnAJsonArray() throws Exception {
        Page<Account> accounts= new PageImpl<Account>(List.of(this.account));
        given(accountService.getAllAccounts(1, 0)).willReturn(accounts);
        this.mvc.perform(
                get("/accounts")).andExpect(
                status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void whenCreateAccountShouldReturnCreated() throws Exception{
        this.postAccount.setUserId(this.mockUser.getId());
        this.mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(this.postAccount))).andExpect((status().isCreated()));
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
   public void getAccountByIban() throws Exception {

        mvc.perform((get("/accounts/" + account.getIBAN()))
        ).andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void callingAllAccountsShouldReturnOK() throws Exception {
    /*
    Removed the servlet context /api for testing
     */
        this.mvc.perform(get("/accounts"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void updateAccountShouldReturnOk() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        accountService.createAccount(this.account);
        this.mvc.perform(put("/accounts/"+this.account.getIBAN()).contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(this.modifyAccountDTO))).andExpect(status().isOk());
    }

    @WithMockUser(username = "employee", roles = {"EMPLOYEE", "CUSTOMERS"})
    @Test
    public void deleteAccountShouldReturnOk() throws Exception
    {
        accountService.createAccount(this.account);
        this.mvc.perform(delete("/accounts/"+this.account.getIBAN())).andExpect(status().isOk());
    }

}
