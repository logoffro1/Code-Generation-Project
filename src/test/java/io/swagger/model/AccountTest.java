package io.swagger.model;

import io.swagger.api.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountTest {


    @MockBean
    private Account account;

    @MockBean
    private User user;

    @Autowired
    private IbanGenerator ibanGenerator;


    @BeforeEach
    public void init() {
        this.user = new User("Egemin", "Cilierli", "egecilierli@gmail.com", "thisisnotapaassword", "+31 06 29297273", User.RoleEnum.ROLE_CUSTOMER);
        this.account = new Account(this.ibanGenerator.generateIban(), BigDecimal.valueOf(200), this.user, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
    }

    //This class is only testing the get and set methods, nothing specific
    @Test
    public void createAccountShouldNotBeNull() {
        Assertions.assertNotNull(account);
    }

    //This had to be checked in the setter so I wrote this test to make sure.
    @Test
    public void balanceMustBeLargerThanAbsoluteLimit() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> this.account.setBalance(
                        account.getAbsoluteLimit().subtract(BigDecimal.valueOf(20)))
        );
    }

    @Test
    public void accountHasCorrectStatus(){
        assertEquals(account.getStatus(), Account.StatusEnum.ACTIVE);
    }

    @Test
    public void accountHasCorrectType(){
        assertEquals(account.getType(), Account.TypeEnum.CURRENT);
    }

    @Test
    public void accountHasCorrectUser(){
        assertEquals(account.getUser(),this.user);
    }

    @Test
    public void accountHasCorrectBalance(){
        assertEquals(this.account.getBalance(),BigDecimal.valueOf(2000));
    }

    @Test
    public void accountHasCorrectAbsoluteLimit(){
        assertEquals(this.account.getAbsoluteLimit(),BigDecimal.valueOf(200));
    }

    @Test
    public void ifBalanceMustBeLargerThanAbsoluteLimit() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> this.account.setBalance(
                        account.getAbsoluteLimit().subtract(BigDecimal.valueOf(20)))
        );
        Assertions.assertEquals("Balance can not be lower than absolute limit",exception.getMessage());
    }



}