package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.TransactionServiceImpl;
import io.swagger.util.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping("/transactions")
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private AccountServiceImpl accountService;


    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        try
        {
            List<TransactionDTO> transactions = new ArrayList<>();
            for (Transaction t : transactionService.getAllTransactions(offset, limit))
                transactions.add(t.getTransactionDTO());

            return new ResponseEntity<List<TransactionDTO>>(HttpStatus.ACCEPTED)
                    .status(200)
                    .body(transactions);
        } catch (NotAcceptableStatusException e)
        {
            e.printStackTrace();
            return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
    public ResponseEntity<CreateTransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody CreateTransactionDTO transactionDTO) {
        try
        {
            Transaction transaction = new Transaction(accountService.getAccountByIbanForTransactions(transactionDTO.getSenderIBAN()),
                    accountService.getAccountByIbanForTransactions(transactionDTO.getReceiverIBAN()), transactionDTO.getAmount(), transactionDTO.getCurrencyType());
            transactionService.createTransaction(transaction);

            return new ResponseEntity<CreateTransactionDTO>(HttpStatus.CREATED).status(201).body(transactionDTO);
        } catch (NotAcceptableStatusException e)
        {
            e.printStackTrace();

            return new ResponseEntity<CreateTransactionDTO>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(transactionDTO);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<TransactionDTO> deleteTransactionByid(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId) {
        try
        {
           //Transaction transaction = transactionService.getTransactionById(transactionId);
            transactionService.deleteTransactionById(transactionId);
            return new ResponseEntity<TransactionDTO>(HttpStatus.ACCEPTED).status(200).body(null);
        } catch (NotAcceptableStatusException e)
        {
            e.printStackTrace();
            return new ResponseEntity<TransactionDTO>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE','CUSTOMER')")
    public ResponseEntity<TransactionDTO> getTransactionById(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Long transactionId) {
        try
        {


            Transaction transaction = transactionService.getTransactionById(transactionId);
            return new ResponseEntity<TransactionDTO>(HttpStatus.ACCEPTED).status(200).body(transaction.getTransactionDTO());
        } catch (NotAcceptableStatusException e)
        {
            e.printStackTrace();
            return new ResponseEntity<TransactionDTO>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
