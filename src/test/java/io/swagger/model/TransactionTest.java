package io.swagger.model;

import io.swagger.service.IbanGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

class TransactionTest
{


    private Transaction transaction;

    @MockBean
    private User mockUser;

    @BeforeEach
    public void init()
    {
      //  User mockUser = new User("Egemin", "Cilierli", "egecilierli@gmail.com", "thisisnotapaassword", "+31 06 29297273", User.RoleEnum.CUSTOMER);
        Account senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        Account receivingAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));

        this.transaction = new Transaction(senderAccount, receivingAccount, 5000.00, "EUR");
    }

    @Test
    public void transactionShouldNotBeNull()
    {
        Assertions.assertNotNull(transaction);
    }

    @Test
    public void transactionHasCorrectCurrencyType()
    {
        Assertions.assertEquals("EUR", this.transaction.getCurrencyType());
    }

    @Test
    public void transactionHasCorrectAmount()
    {
        Assertions.assertEquals((Double) 5000.00, this.transaction.getAmount());
    }
}