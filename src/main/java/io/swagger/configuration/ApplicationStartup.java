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
public class ApplicationStartup implements ApplicationRunner {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private IbanGeneratorService ibanService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User("John", "Doe", "JohnDoe@gmail.com", "johnnie123", "213712983", User.RoleEnum.ROLE_EMPLOYEE);
        User bank = new User("Bank", "NL", "BankNl@gmail.com", "BankPassword", "+31 06 929281802", 1000000.00, 1000.00, User.RoleEnum.ROLE_EMPLOYEE);


        userService.createUser(user);
        userService.createUser(bank);

        //This account has a static iban for cucumber testing. I need to have a non generated iban for testing purposes.
        Account sender = new Account("NL03INHO0778852693", BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020));
        Account receiver = new Account("NL04INHO0836583990", BigDecimal.valueOf(0), user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2020));
        Account bankAccount = new Account("NL01INHO00000001", BigDecimal.valueOf(0), bank, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(100000));
        accountService.createAccount(sender);
        accountService.createAccount(receiver);
        accountService.createAccount(bankAccount);


        User customer = new User("Williams", "smith", "willliamSmith@gmail.com", "william123", "213712983", User.RoleEnum.ROLE_CUSTOMER);

        userService.createUser(customer);
        customer.setTransactionLimit(4000.00);
        customer.setDayLimit(3000.00);
        Account sender2 = new Account("NL03INHO0778852694", BigDecimal.valueOf(0), customer, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        Account receiver2 = new Account("NL04INHO0836583995", BigDecimal.valueOf(0), customer, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        Account closedAccount = new Account("NL04INHO0836583997", BigDecimal.valueOf(0), user, Account.TypeEnum.SAVINGS, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));

        accountService.createAccount(sender2);
        accountService.createAccount(receiver2);
        accountService.createAccount(closedAccount);
        closedAccount.setStatus(Account.StatusEnum.CLOSED);

        Transaction transaction1 = new Transaction(sender, receiver, 1000.00, "EUR");
        transactionService.createTransaction(transaction1);

        Transaction transaction2 = new Transaction(sender2, receiver2, 1000.00, "EUR");
        transactionService.createTransaction(transaction2);

    }
}
