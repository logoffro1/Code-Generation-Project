package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.service.AccountServiceImpl;
import io.swagger.model.IbanGenerator;
import io.swagger.service.IbanGeneratorService;
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
    private IbanGeneratorService gen;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        User user = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", 5000.00, 2000.00, User.RoleEnum.CUSTOMER);
        userService.createUser(user);
       Account account1 = new Account(gen.generateIban(), BigDecimal.valueOf(00), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
       Account account2 = new Account(gen.generateIban(), BigDecimal.valueOf(00), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020));
        accountServiceImpl.createAccount(account1);
        accountServiceImpl.createAccount(account2);
//        transactionServiceImpl.createTransaction(new Transaction(accountServiceImpl.getAccountById(2), accountServiceImpl.getAccountById(3), 100.00, "EUR"));

transactionServiceImpl.createTransaction(new Transaction(account1,account2,2000.00,"EUR"));
    }
}
