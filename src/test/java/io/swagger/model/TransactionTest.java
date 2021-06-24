package io.swagger.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TransactionTest
{


    private TransactionDTO transaction;

    @MockBean
    private User mockUser;

    @BeforeEach
    public void init()
    {
        Account senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        Account receivingAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));

        this.transaction = new TransactionDTO(senderAccount.getUser().getId(),senderAccount.getIBAN(), receivingAccount.getIBAN(), 5000.00, "EUR");
    }

    @Test
    public void transactionShouldNotBeNull()
    {
        Assertions.assertNotNull(transaction);
    }

    @Test
    public void transactionHasCorrectCurrencyType()
    {
        Assertions.assertEquals("EUR", transaction.getCurrencyType());
    }

    @Test
    public void transactionHasCorrectAmount()
    {
        Assertions.assertEquals((Double) 5000.00, transaction.getAmount());
    }

    @Test
    public void transactionAmountShouldBeSetCorrectly(){
        transaction.setAmount(1000.00);
        Assertions.assertEquals((Double)1000.00,transaction.getAmount());
    }

    @Test
    public void transactionCurrencyTypeShouldBeSetCorrectly(){
        transaction.setCurrencyType("RON");
        Assertions.assertEquals("RON",transaction.getCurrencyType());
    }

    @Test
    public void transactionHasCorrectSenderIBAN(){
        Assertions.assertEquals("iban1",transaction.getSenderIBAN());
    }
    @Test
    public void transactionHasCorrectReceiverIBAN(){
        Assertions.assertEquals("iban2",transaction.getReceiverIBAN());
    }
@Test
    public void settingAmountBelowZeroShouldThrowException(){
    Exception exception = assertThrows(IllegalArgumentException.class,
            () -> transaction.setAmount(-0.00));
    Assertions.assertEquals("Amount cannot be less or equal to zero.", exception.getMessage());
}
}