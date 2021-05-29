package io.swagger.api;

import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.IbanGenerator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
public class AccountApiController implements AccountApi {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private IbanGenerator ibanGenerator;

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Account> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be created", schema=@Schema()) @Valid @RequestBody Account newAccount) {
        String accept = request.getHeader("Accept");
        newAccount.setIBAN(this.ibanGenerator.generateIban());
        accountServiceImpl.createAccount(newAccount);
        return new ResponseEntity<Account>(HttpStatus.CREATED).status(201).body(newAccount);
    }

    public ResponseEntity<Account> editAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the account you want to edit", required=true, schema=@Schema()) @PathVariable("userId") Integer userId, @Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be edited", schema=@Schema()) @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        Account oldAccount = accountServiceImpl.getAccountById(userId);
        //since when there is the same id, only rows get updated with save. We can simply call save method.
        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> getAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the user who owns the account", required=true, schema=@Schema()) @PathVariable("userId") Integer userId,@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "active", "closed" }
)) @Valid @RequestParam(value = "status", required = true) String status) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"IBAN\",\n  \"balance\" : 6.027456183070403,\n  \"type\" : \"current\",\n  \"userId\" : 0,\n  \"status\" : \"active\",\n  \"token\" : \"token\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> getAccountStatus(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema(allowableValues={ "active", "closed" }
)) @PathVariable("status") String status,@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"IBAN\",\n  \"balance\" : 6.027456183070403,\n  \"type\" : \"current\",\n  \"userId\" : 0,\n  \"status\" : \"active\",\n  \"token\" : \"token\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> getAccountType(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema(allowableValues={ "current", "savings" }
)) @PathVariable("type") String type,@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"IBAN\" : \"IBAN\",\n  \"balance\" : 6.027456183070403,\n  \"type\" : \"current\",\n  \"userId\" : 0,\n  \"status\" : \"active\",\n  \"token\" : \"token\"\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

}
