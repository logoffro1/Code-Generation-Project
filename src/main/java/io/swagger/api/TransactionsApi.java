/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@Validated
public interface TransactionsApi {

    @Operation(summary = "", description = "Get all current transactions", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Transaction created", content = @Content(schema = @Schema(implementation = Transaction.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),
        
        @ApiResponse(responseCode = "404", description = "Not found"),
        
        @ApiResponse(responseCode = "409", description = "Conflict"),
        
        @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Transaction> transactionsGet(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit);


    @Operation(summary = "", description = "Create a transaction", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Transaction Created", content = @Content(schema = @Schema(implementation = Transaction.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),
        
        @ApiResponse(responseCode = "409", description = "Conflict"),
        
        @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Transaction> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Transaction body);


    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully deleted."),
        
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),
        
        @ApiResponse(responseCode = "404", description = "Not found"),
        
        @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "/{transactionId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> transactionsTransactionIdDelete(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId);


    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully received the transactions of transactionId", content = @Content(schema = @Schema(implementation = Transaction.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),
        
        @ApiResponse(responseCode = "404", description = "Not found"),
        
        @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "/{transactionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Transaction> transactionsTransactionIdGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId);


    @Operation(summary = "", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Transactions" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Transaction modified."),
        
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        
        @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),
        
        @ApiResponse(responseCode = "404", description = "Not found"),
        
        @ApiResponse(responseCode = "409", description = "Conflict"),
        
        @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "/{transactionId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> transactionsTransactionIdPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Transaction body);

}

