package io.swagger.configuration;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
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
public class ApplicationStartup implements ApplicationRunner
{

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IbanGeneratorService ibanService;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {

        User user = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.ROLE_CUSTOMER);
        User bank = new User("Bank","NL","BankNl@gmail.com","BankPassword","+31 06 929281802",1000000.00,1000.00,User.RoleEnum.ROLE_EMPLOYEE);
        userService.createUser(user);
        userService.createUser(bank);

        Account sender = new Account(ibanService.generateIban(), BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020));
        Account receiver = new Account(ibanService.generateIban(), BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020));
        Account bankAccount= new Account("NL01INHO00000001",BigDecimal.valueOf(0),bank, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE,BigDecimal.valueOf(100000));
        accountService.createAccount(sender);
        accountService.createAccount(receiver);
        accountService.createAccount(bankAccount);
        userService.createUser(new User("Williams", "smith", "willliamSmith@gmail.com", "william123", "213712983", User.RoleEnum.ROLE_EMPLOYEE));
        transactionService.createTransaction(new Transaction(sender,receiver,1000.00,"EUR"));
    }
}
