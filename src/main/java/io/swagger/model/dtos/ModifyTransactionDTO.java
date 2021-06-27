package io.swagger.model.dtos;

import lombok.NonNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

public class ModifyTransactionDTO {

    private double amount;

    public ModifyTransactionDTO() {
    }

    public ModifyTransactionDTO(@NonNull Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
