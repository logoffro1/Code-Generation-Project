package io.swagger.model;

import io.swagger.exceptions.ApiRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TransactionTest {


    private Transaction transaction;

    @MockBean
    private User mockUser;

    Account senderAccount;
    Account receivingAccount;

    @BeforeEach
    public void init() {
        this.senderAccount = new Account("iban1", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));
        this.receivingAccount = new Account("iban2", BigDecimal.valueOf(0), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(10000));

        this.transaction = new Transaction(senderAccount, receivingAccount, 5000.00, "EUR");
    }

    @Test
    public void transactionShouldNotBeNull() {
        Assertions.assertNotNull(transaction);
    }

    @Test
    public void transactionHasCorrectCurrencyType() {
        Assertions.assertEquals("EUR", transaction.getCurrencyType());
    }

    @Test
    public void transactionHasCorrectAmount() {
        Assertions.assertEquals((Double) 5000.00, transaction.getAmount());
    }

    @Test
    public void transactionAmountShouldBeSetCorrectly() {
        transaction.setAmount(1000.00);
        Assertions.assertEquals((Double) 1000.00, transaction.getAmount());
    }

    @Test
    public void transactionCurrencyTypeShouldBeSetCorrectly() {
        transaction.setCurrencyType("RON");
        Assertions.assertEquals("RON", transaction.getCurrencyType());
    }

    @Test
    public void transactionHasCorrectSenderIBAN() {
        Assertions.assertEquals("iban1", transaction.getSenderAccount().getIBAN());
    }

    @Test
    public void transactionHasCorrectReceiverIBAN() {
        Assertions.assertEquals("iban2", transaction.getReceiverAccount().getIBAN());
    }

    @Test
    public void settingAmountBelowZeroShouldThrowException() {
        Exception exception = assertThrows(ApiRequestException.class,
                () -> transaction.setAmount(-1.00));
        Assertions.assertEquals("Amount cannot be less or equal to zero.", exception.getMessage());
    }

    @Test
    public void transactionId() {
        transaction = transaction.transactionId(30);
        Assertions.assertEquals(30, transaction.getTransactionId());
    }

    @Test
    public void setTransactionId() {

        transaction.setTransactionId(13);
        Assertions.assertEquals(13, transaction.getTransactionId());
    }

    @Test
    public void dateTimeCreated() {
        OffsetDateTime expected = OffsetDateTime.now();
        transaction = transaction.dateTimeCreated(expected);

        Assertions.assertEquals(expected, transaction.getDateTimeCreated());
    }

    @Test
    public void setDateTimeCreated() {
        OffsetDateTime expected = OffsetDateTime.now();
        transaction.setDateTimeCreated(expected);

        Assertions.assertEquals(expected, transaction.getDateTimeCreated());
    }

    @Test
    public void senderAccount() {
        transaction = transaction.senderAccount(this.receivingAccount);
        transaction.setReceiverAccount(this.senderAccount);
        Assertions.assertEquals(receivingAccount,transaction.getSenderAccount());
    }

    @Test
    public void receiverAccount(){
        transaction = transaction.receiverAccount(this.senderAccount);
        transaction.setSenderAccount(this.receivingAccount);
        Assertions.assertEquals(senderAccount,transaction.getReceiverAccount());
    }
}