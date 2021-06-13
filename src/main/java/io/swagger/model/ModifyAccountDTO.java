package io.swagger.model;

import lombok.NonNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

public class ModifyAccountDTO {

    private Account.TypeEnum type;

    public ModifyAccountDTO() {
    }

    public ModifyAccountDTO(@NonNull Account.TypeEnum type) {
        this.type = type;
    }

    public Account.TypeEnum getType() {
        return type;
    }

    public void setType(Account.TypeEnum type) {
        this.type = type;
    }

}
