package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping("/accounts")
public class AccountApiController implements AccountApi {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private UserServiceImpl userService;

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

    //POST endpoint
    @RequestMapping(value = "",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ResponseAccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be created", schema = @Schema()) @Valid @RequestBody CreateAccountDTO newAccount) {
        try{
            User realUser= userService.getUserById(newAccount.getUserId());
            Account account= new Account(this.ibanGenerator.generateIban(),newAccount.getAbsoluteLimit(),realUser,newAccount.getType(),newAccount.getStatus(),newAccount.getBalance());
            accountServiceImpl.createAccount(account);
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.CREATED).status(201).body(accountServiceImpl.convertToResponseAccountDTO(account));
        }
        catch(NotAcceptableStatusException exception){
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    PUT by iban endpoint
    @RequestMapping(value = "/{iban}",
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<ResponseAccountDTO> editAccountByIban(@Parameter(in = ParameterIn.PATH, description = "the id of the account you want to edit", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be edited", schema = @Schema()) @Valid @RequestBody ModifyAccountDTO modifiedDTOAccount) {
        try{

            Account dbAccount = accountServiceImpl.getAccountByIban(iban);

            Account updatedAccount= accountServiceImpl.updateAccount(dbAccount,modifiedDTOAccount);
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.ACCEPTED).status(200).body(accountServiceImpl.convertToResponseAccountDTO(dbAccount));
        }catch (NotAcceptableStatusException exception){
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

//    GET by iban endpoint
    @RequestMapping(value = "/{iban}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
    public ResponseEntity<ResponseAccountDTO> getAccountByIban(@Parameter(in = ParameterIn.PATH, description = "the id of the user who owns the account", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        try{
            Account account= accountServiceImpl.getAccountByIban(iban);
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.OK).status(HttpStatus.OK).body(accountServiceImpl.convertToResponseAccountDTO(account));
        }catch (NotAcceptableStatusException exception){
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.NOT_FOUND).status(HttpStatus.NOT_FOUND).body(null);
        }

    }

//GET endpoint for all Accounts
    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Page<ResponseAccountDTO>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        try
        {
            Page<Account> allAccounts = accountServiceImpl.getAllAccounts(limit,offset);
            return new ResponseEntity<Page<ResponseAccountDTO>>(HttpStatus.ACCEPTED).status(200).body(accountServiceImpl.convertPageToResponseAccountPage(allAccounts));
        }catch (NotAcceptableStatusException exception){
            return new ResponseEntity<List<ResponseAccountDTO>>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    //DELETE by iban endpoint

    @PreAuthorize("hasRole('EMPLOYEE')")
    public  ResponseEntity<ResponseAccountDTO> deleteAccount(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban){
        try{
            Account changedAccount= accountServiceImpl.softDeleteAccount(iban);
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.ACCEPTED).status(200).body(accountServiceImpl.convertToResponseAccountDTO(changedAccount));
        }catch (NotAcceptableStatusException exception) {
            return new ResponseEntity<ResponseAccountDTO>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
