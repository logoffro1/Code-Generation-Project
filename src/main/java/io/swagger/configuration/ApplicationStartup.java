package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.service.AccountService;
import io.swagger.service.LoginService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

//Written by Egehan Cinarli.
//This class is ran at the once the application starts running. Mostly being used to enter the initial data to the database.
@Component
@Log
@Transactional
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private  AccountService accountService;
    @Autowired
    private LoginService loginService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = userService.createUser(new User("John","Doe","JohnDoe@gmail.com","johnnie123","213712983", User.RoleEnum.ROLE_EMPLOYEE));
        log.info(user.toString());
//        String token = loginService.login(user.getEmail(), user.getPassword());
//        log.info("Token " + token);

        accountService.createAccount(new Account("12312",422.00,1, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(2020),"token"));

        userService.createUser(new User("Williams","smith","willliamSmith@gmail.com","william123","213712983", User.RoleEnum.ROLE_CUSTOMER));
    }
}
