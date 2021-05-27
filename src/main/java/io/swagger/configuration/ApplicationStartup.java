package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;

import javax.transaction.Transactional;
import java.math.BigDecimal;

//Written by Egehan Cinarli.
//This class is ran at the once the application starts running. Mostly being used to enter the initial data to the database.
@Component
@Transactional
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private  AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        userService.createUser(new User(1,"John","Doe","JohnDoe@gmail.com","johnnie123","213712983", User.RoleEnum.CUSTOMER));
        accountService.createAccount(new Account(2,"NL1291232112902",1, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(2020),"token"));
    }
}
