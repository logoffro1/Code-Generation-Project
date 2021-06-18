/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.CreateAccountDTO;
import io.swagger.model.ModifyAccountDTO;
import io.swagger.model.ResponseAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@Validated
public interface AccountApi {

    @Operation(summary = "Creates an account", description = "Creates an account for a normal user only the ID is needed", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts"})
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success"),
        
        @ApiResponse(responseCode = "400", description = "account was not found"),
        
        @ApiResponse(responseCode = "5XX", description = "unexpected error") })
    @RequestMapping(value = "",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<ResponseAccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be created", schema=@Schema()) @Valid @RequestBody CreateAccountDTO body);


    @Operation(summary = "edit existed account", description = "get the account by id to edit the info of the account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success") })
    @RequestMapping(value = "/{iban}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<ResponseAccountDTO> editAccountByIban(@Parameter(in = ParameterIn.PATH, description = "the id of the account you want to edit", required=true, schema=@Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be edited", schema=@Schema()) @Valid @RequestBody ModifyAccountDTO body);


    @Operation(summary = "get account by Iban", description = "get account by Iban", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Account.class))) })
    @RequestMapping(value = "/{iban}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ResponseAccountDTO> getAccountByIban(@Parameter(in = ParameterIn.PATH, description = "the id of the user who owns the account", required=true, schema=@Schema()) @PathVariable("iban") String iban);



    @Operation(summary = "Get accounts", description = "Get the Accounts according to offset and limit values", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "account was not found"),

            @ApiResponse(responseCode = "5XX", description = "unexpected error") })
    @RequestMapping(value = "",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Page<ResponseAccountDTO>> getAccounts(@Parameter(in = ParameterIn.QUERY, description = "The number of items to skip before starting to collect the query results" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit);

    @Operation(summary = "", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted."),

            @ApiResponse(responseCode = "400", description = "Invalid input"),

            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have access rights"),

            @ApiResponse(responseCode = "404", description = "Not found"),

            @ApiResponse(responseCode = "500", description = "Oops, something went wrong on the server.") })
    @RequestMapping(value = "/{iban}",
            method = RequestMethod.DELETE)
    ResponseEntity<ResponseAccountDTO> deleteAccount(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban);
}




