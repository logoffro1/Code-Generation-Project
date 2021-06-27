package io.swagger.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {

    @JsonProperty("Token")
    private String token = null;

    public TokenDTO token(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
