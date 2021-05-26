/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T00:05:39.615Z[GMT]")
@Validated
public interface AccountApi {

    @Operation(summary = "Creates an account", description = "Creates an account for a normal user", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success"),
        
        @ApiResponse(responseCode = "400", description = "account was not found"),
        
        @ApiResponse(responseCode = "5XX", description = "unexpected error") })
    @RequestMapping(value = "/account",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be created", schema=@Schema()) @Valid @RequestBody Account body);


    @Operation(summary = "edit existed account", description = "get the account by id to edit the info of the account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success") })
    @RequestMapping(value = "/account/{userId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> editAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the account you want to edit", required=true, schema=@Schema()) @PathVariable("userId") Integer userId, @Parameter(in = ParameterIn.DEFAULT, description = "description of the body of the account to be edited", schema=@Schema()) @Valid @RequestBody Account body);


    @Operation(summary = "get account by id", description = "get account by id", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Account.class))) })
    @RequestMapping(value = "/account/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Account> getAccountById(@Parameter(in = ParameterIn.PATH, description = "the id of the user who owns the account", required=true, schema=@Schema()) @PathVariable("userId") Integer userId, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "active", "closed" }
)) @Valid @RequestParam(value = "status", required = true) String status);


    @Operation(summary = "get status", description = "get status of a specific account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful", content = @Content(schema = @Schema(implementation = Account.class))),
        
        @ApiResponse(responseCode = "400", description = "account was not found"),
        
        @ApiResponse(responseCode = "5XX", description = "unexpected error") })
    @RequestMapping(value = "/account/{status}/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Account> getAccountStatus(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema(allowableValues={ "active", "closed" }
)) @PathVariable("status") String status, @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId);


    @Operation(summary = "account type", description = "get the type of the account (either current or savings)", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Accounts" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = Account.class))),
        
        @ApiResponse(responseCode = "400", description = "account was not found"),
        
        @ApiResponse(responseCode = "5XX", description = "unexpected error") })
    @RequestMapping(value = "/account/{type}/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Account> getAccountType(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema(allowableValues={ "current", "savings" }
)) @PathVariable("type") String type, @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("userId") Integer userId);

}
