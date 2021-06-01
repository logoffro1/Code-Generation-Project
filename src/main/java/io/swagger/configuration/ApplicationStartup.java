package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.IbanGenerator;
import io.swagger.service.TransactionServiceImpl;
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
    private TransactionServiceImpl transactionServiceImpl;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private IbanGenerator gen;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        User user = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", 5000.00, 1.000, User.RoleEnum.CUSTOMER);
        userService.createUser(user);
        accountServiceImpl.createAccount(new Account(gen.generateIban(), 0.00, user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020), "token"));
        accountServiceImpl.createAccount(new Account(gen.generateIban(), 0.00, user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020), "token"));
        transactionServiceImpl.createTransaction(new Transaction(accountServiceImpl.getAccountById(2), accountServiceImpl.getAccountById(3), 100.00, "EUR"));

    }
}
