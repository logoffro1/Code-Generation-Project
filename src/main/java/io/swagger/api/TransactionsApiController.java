package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountServiceImpl;
import io.swagger.service.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping("/transactions")
public class TransactionsApiController implements TransactionsApi
{

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionServiceImpl transactionService;


    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request)
    {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Transaction>> getAllTransactions(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results", schema = @Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return", schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit)
    {
        try
        {
            return new ResponseEntity<List<Account>>(HttpStatus.ACCEPTED)
                    .status(200)
                    .body(transactionService.getAllTransactions(offset, limit));
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public ResponseEntity<Transaction> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Transaction transaction)
    {
        try
        {
            if (transactionService.createTransaction(transaction) == null)
                return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);

            return new ResponseEntity<Transaction>(HttpStatus.CREATED).status(201).body(transaction);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public ResponseEntity<Transaction> deleteTransactionByid(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId)
    {
        try
        {
            Transaction transaction = transactionService.deleteTransactionById(transactionId);
            return new ResponseEntity<Account>(HttpStatus.ACCEPTED).status(200).body(transaction);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public ResponseEntity<Transaction> getTransactionById(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId)
    {
        try
        {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED).status(200).body(transaction);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    public ResponseEntity<Transaction> updateTransactionById(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Transaction newTransaction)
    {
        try
        {
            Transaction transaction = transactionService.updateTransaction(transactionId, newTransaction);
            return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED).status(200).body(transaction);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

}
