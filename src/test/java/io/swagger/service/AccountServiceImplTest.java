package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AccountServiceImplTest {


    @Mock
    private AccountRepository accountRepo;
    private AutoCloseable autoCloseable;


    @Autowired
    @InjectMocks
    private AccountServiceImpl accountServiceImpl;
    private List<Account> accountList;


    private Account account;

    private AccountDTO postAccount;

    private ModifyAccountDTO modifyAccountDTO;

    private User mockUser;

    @BeforeEach
     void initialize(){
        mockUser = new User("firstName","lastName","email","password","090078601", User.RoleEnum.EMPLOYEE);
        mockUser.setId(1003);

        modifyAccountDTO= new ModifyAccountDTO(Account.TypeEnum.CURRENT);
        this.account = new Account("iban", BigDecimal.valueOf(200), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
        this.postAccount= new AccountDTO(BigDecimal.valueOf(200),mockUser, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000), Account.TypeEnum.CURRENT);
    }


//    @AfterEach
//     void tearDown()
//    {
//        this.mockUser=null;
//        this.accountList =null;
//    }

    @Test
    void getAccountByIban() {

    }

    @Test
    void updateAccount() {
    }

    @Test
    void getAllAccounts() {
        Account account1= new Account("iban1",postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        Account account2= new Account("iban2",postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        accountServiceImpl.createAccount(account1);
        accountServiceImpl.createAccount(account2);

        List<Account>accounts1 = accountServiceImpl.getAllAccounts(2,0);
        verify(accountRepo.findAll());


    }

    @Test
    void createdAccountShouldntHaveLessBalanceThanAbsoluteLimit(){
        postAccount.setBalance(postAccount.getAbsoluteLimit().subtract(BigDecimal.valueOf(10)));
        Account account1= new Account("iban1",postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> accountServiceImpl.createAccount(account1));
        Assertions.assertEquals("Balance can not be less than absoluteLimit", exception.getMessage());

    }
    @Test
    void createdAccountShouldntHaveNullUser(){
        postAccount.setUser(null);
        Account account1= new Account("iban1",postAccount.getAbsoluteLimit(),postAccount.getUser(),postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> accountServiceImpl.createAccount(account1));
        Assertions.assertEquals(null, exception.getMessage());

    }

    @Test
    void createAccountShouldNotReturnNull() {

        Account account1= new Account("iban1",postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        accountServiceImpl.createAccount(account1);
    }

    @Test
    void isIbanPresent() {
        accountServiceImpl.createAccount(this.account);

        Mockito.when(accountRepo.findByIBAN(this.account.getIBAN())).thenReturn(this.account);

        boolean result =accountServiceImpl.isIbanPresent(this.account.getIBAN());

        assertTrue(result);
    }

    @Test
    void softDeleteAccount() {
    }
}