package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.service.AccountService;
import io.swagger.service.IbanGeneratorService;
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
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private  AccountService accountService;
    @Autowired
    private IbanGeneratorService ibanService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user=new User("John","Doe","JohnDoe@gmail.com","johnnie123","213712983", User.RoleEnum.CUSTOMER);

        userService.createUser(user);

        accountService.createAccount(new Account(ibanService.generateIban(),BigDecimal.valueOf(2020),user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(2020)));
        accountService.createAccount(new Account(ibanService.generateIban(),BigDecimal.valueOf(2020),user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(2020)));
        accountService.createAccount(new Account(ibanService.generateIban(),BigDecimal.valueOf(2020),user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(2020)));

        userService.createUser(new User("Williams","smith","willliamSmith@gmail.com","william123","213712983", User.RoleEnum.CUSTOMER));

    }
}
