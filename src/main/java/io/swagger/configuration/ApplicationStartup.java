package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.service.AccountService;
import io.swagger.service.IbanGenerator;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

//Written by Egehan Cinarli.
//This class is ran at the once the application starts running. Mostly being used to enter the initial data to the database.
@Component
@Transactional
public class ApplicationStartup implements ApplicationRunner
{

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IbanGenerator gen;
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        User user = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983",5000.00,1.000, User.RoleEnum.CUSTOMER);
        userService.createUser(user);
        accountService.createAccount(new Account(gen.generateIban(),0.00, user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020), "token"));
        accountService.createAccount(new Account(gen.generateIban(),0.00, user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020), "token"));
        transactionService.createTransaction(new Transaction(accountService.getAccountById(2), accountService.getAccountById(3), 100.00, "EUR"));



    }
}
