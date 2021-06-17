package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class AccountDTO {

    private BigDecimal absoluteLimit;

    private User user;


    private Account.StatusEnum status;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Account.TypeEnum type;

    public AccountDTO() {
    }

    public AccountDTO(BigDecimal absoluteLimit,User user, Account.StatusEnum status, BigDecimal balance, Account.TypeEnum type) {
        this.absoluteLimit = absoluteLimit;
        this.user = user;
        this.status = status;
        this.balance = balance;
        this.type = type;
    }

    public BigDecimal getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(BigDecimal absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account.StatusEnum getStatus() {
        return status;
    }

    public void setStatus(Account.StatusEnum status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account.TypeEnum getType() {
        return type;
    }

    public void setType(Account.TypeEnum type) {
        this.type = type;
    }
}
