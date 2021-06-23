package io.swagger.model;

import io.swagger.api.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {


    private Account account;


    @BeforeEach
    public void init() {
        User mockUser = new User("Egemin", "Cilierli", "egecilierli@gmail.com", "thisisnotapaassword", "+31 06 29297273", User.RoleEnum.ROLE_CUSTOMER);
        this.account = new Account("NL02INHO00123456789", BigDecimal.valueOf(200), mockUser, Account.TypeEnum.CURRENT, Account.StatusEnum.ACTIVE, BigDecimal.valueOf(2000));
    }

    @Test
    public void createAccountShouldNotBeNull() {
        Assertions.assertNotNull(account);
    }

    @Test
    public void balanceMustBeLargerThanAbsoluteLimit() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> this.account.setBalance(
                        account.getAbsoluteLimit().subtract(BigDecimal.valueOf(20)))
        );
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