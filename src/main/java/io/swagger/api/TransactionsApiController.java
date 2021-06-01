package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.util.ArrayList;
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
        List<Transaction> sortedTransactions = new ArrayList<Transaction>();

        int count = 0;
        for (Transaction t : transactionService.getAllTransactions())
        {
            if (limit-- == 0) break;
            if (count++ < offset) continue;

            sortedTransactions.add(t);
        }

        return new ResponseEntity<List<Account>>(HttpStatus.ACCEPTED).status(200).body(sortedTransactions);
    }

    public ResponseEntity<Transaction> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Transaction body)
    {


        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteTransactionByid(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId)
    {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Transaction> getTransactionById(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId)
    {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json"))
        {
            try
            {
                return new ResponseEntity<Transaction>(objectMapper.readValue("{\n  \"currencyType\" : \"EUR\",\n  \"amountLimit\" : 4800,\n  \"dateTimeCreated\" : \"2000-01-23T04:56:07.000+00:00\",\n  \"amount\" : 4800,\n  \"senderUserId\" : 20939,\n  \"senderIBAN\" : \"NL23RABO2298608059\",\n  \"receiverIBAN\" : \"NL67ABNA8265634552\",\n  \"transactionId\" : 2012\n}", Transaction.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e)
            {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateTransactionById(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody Transaction body)
    {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
