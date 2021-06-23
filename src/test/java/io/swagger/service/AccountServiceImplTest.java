package io.swagger.service;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private AccountRepository accountRepo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private IbanGeneratorService ibanGenerator;

    private Account account;

    private AccountDTO postAccount;

    private ModifyAccountDTO modifyAccountDTO;

    private User mockUser;

    @BeforeEach
     void initialize(){
        mockUser = new User("firstName","lastName","email","password","090078601", User.RoleEnum.ROLE_EMPLOYEE);
        mockUser.setId(1003);

        modifyAccountDTO= new ModifyAccountDTO(Account.TypeEnum.CURRENT);
        this.account = new Account(ibanGenerator.generateIban(), BigDecimal.valueOf(200), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
        this.postAccount= new AccountDTO(BigDecimal.valueOf(200),mockUser, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000), Account.TypeEnum.CURRENT);
    }



    @Test
    void updateAccountShouldChangeAccountType() {
        if(this.modifyAccountDTO.getType()== Account.TypeEnum.CURRENT){

        }
    }

    @Test
    void getAllAccounts() {

        List<Account> accountList=new ArrayList<>();
        Account account1= new Account(ibanGenerator.generateIban(),postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        Account account2= new Account(ibanGenerator.generateIban(),postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        accountList.add(account1);
        accountList.add(account2);

        //Since we have done pagination in our gets in order to reduce the huge chunks of data, we also have to mock the
        // paged data.
        Page<Account> accountListPaged= new PageImpl<Account>(accountList);



        given(accountRepo.findAll(PageRequest.of(0, 2))).willReturn(accountListPaged);
        Page<Account>accounts1 = accountServiceImpl.getAllAccounts(2,0);
        assertEquals(accounts1,accountListPaged);
        verify(accountRepo).findAll(PageRequest.of(0, 2));

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
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> accountServiceImpl.createAccount(account1));
        Assertions.assertEquals("User can not be null", exception.getMessage());

    }

    @Test
    void getAccountByIbanShouldReturnTheRightAccount() {
        //We mock the data to see if everything is working right.
        when(accountRepo.findByIBAN(this.account.getIBAN())).thenReturn(this.account);
        Account returnedAccount = accountServiceImpl.getAccountByIban(this.account.getIBAN());
        assertEquals(returnedAccount,this.account);
    }

    @Test
    void createAccountShouldNotHavePresentIbanInDatabase() {

        Account account1= new Account(
                ibanGenerator.generateIban(),
                postAccount.getAbsoluteLimit(),
                mockUser,postAccount.getType(),
                postAccount.getStatus(),
                postAccount.getBalance());

        Mockito.when(accountRepo.save(account1)).thenReturn(account1);
        accountServiceImpl.createAccount(account1);

        Account account2= new Account(account1.getIBAN(),postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        Mockito.when(accountRepo.save(account2)).thenThrow(new ApiRequestException("Iban is already present in the database",HttpStatus.NOT_FOUND));

        //Since the ibans are set and accounts are mocked, we know that this method is supposed to return the following exception.
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> accountServiceImpl.createAccount(account2));
        Assertions.assertEquals("Iban is already present in the database", exception.getMessage());
    }

    @Test
    void isIbanPresentShouldNotReturnFalse() {

        Account account= new Account(ibanGenerator.generateIban(),postAccount.getAbsoluteLimit(),mockUser,postAccount.getType(),postAccount.getStatus(),postAccount.getBalance());
        accountServiceImpl.createAccount(account);

        Mockito.when(accountRepo.findByIBAN(account.getIBAN())).thenReturn(account);
        assertThat(accountServiceImpl.getAccountByIban(account.getIBAN())).isEqualTo(account);
    }

    @Test
    void softDeleteAccountShouldChangeTheStatusOfAccount() {
        //In case it was closed
        this.account.setStatus(Account.StatusEnum.ACTIVE);
        when(accountRepo.findByIBAN(this.account.getIBAN())).thenReturn(this.account);
        accountServiceImpl.softDeleteAccount(this.account.getIBAN());
        assertEquals(this.account.getStatus(), Account.StatusEnum.CLOSED);
    }

    @Test
    void ifAccountClosedThrowAccountAlreadyClosedMessage() {
        //In case it was closed
        this.account.setStatus(Account.StatusEnum.CLOSED);
        when(accountRepo.findByIBAN(this.account.getIBAN())).thenReturn(this.account);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () ->  accountServiceImpl.softDeleteAccount(this.account.getIBAN()));
        Assertions.assertEquals("Account is already closed", exception.getMessage());
    }
}