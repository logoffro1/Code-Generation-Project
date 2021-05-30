package io.swagger.api;

import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Response;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping("/accounts")
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

    //Done and working
    @RequestMapping(value = "/accounts",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Account> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be created", schema = @Schema()) @Valid @RequestBody Account newAccount) {
        String accept = request.getHeader("Accept");
        newAccount.setIBAN(this.ibanGenerator.generateIban());
        accountServiceImpl.createAccount(newAccount);
        return new ResponseEntity<Account>(HttpStatus.CREATED).status(201).body(newAccount);
    }

    //Usable method however doesn't work
    @RequestMapping(value = "/accounts/{accountId}",
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    public ResponseEntity<Account> editAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the account you want to edit", required = true, schema = @Schema()) @PathVariable("accountId") Integer accountId, @Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be edited", schema = @Schema()) @Valid @RequestBody Account updatedAccount) {
        String accept = request.getHeader("Accept");

        //since when there is the same id, only rows get updated with save. We can simply call save method.
        Account dbAccount = accountServiceImpl.getAccountById(accountId);
        dbAccount = updatedAccount;
        //This method will not create any new rows. It will simply update the row with the ID.
        accountServiceImpl.createAccount(dbAccount);
        return new ResponseEntity<Account>(HttpStatus.ACCEPTED).status(200).body(dbAccount);
    }

    //Work on this next
    @RequestMapping(value = "/accounts/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the user who owns the account", required = true, schema = @Schema()) @PathVariable("accountId") Integer accountId, @NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema(allowableValues = {"active", "closed"}
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

    @RequestMapping(value = "/accounts/{status}/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Account> getAccountStatus(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema(allowableValues = {"active", "closed"}
    )) @PathVariable("status") String status, @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("userId") Integer userId) {
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


    //Done and working
    @RequestMapping(value = "/accounts",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<Account> allAccounts = accountServiceImpl.getAllAccounts();
        List<Account> sortedAccounts = new ArrayList<Account>();
        for (int i = 0; i < allAccounts.stream().count(); i++) {

            if (i < offset) {
                continue;
            }

            if (limit==0) {
                break;
            } else {
                sortedAccounts.add(allAccounts.get(i));
                limit--;
            }

        }
        for(Account c: sortedAccounts){
            System.out.println(c);
        }
        return new ResponseEntity<List<Account>>(HttpStatus.ACCEPTED).status(200).body(sortedAccounts);
    }


}
