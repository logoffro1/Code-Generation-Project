///**
// * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
// * https://github.com/swagger-api/swagger-codegen
// * Do not edit the class manually.
// */
//package io.swagger.api;
//
//import io.swagger.model.Body;
//import io.swagger.model.InlineResponse200;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.bind.annotation.CookieValue;
//
//import javax.validation.Valid;
//import javax.validation.constraints.*;
//import java.util.List;
//import java.util.Map;
//
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
//@Validated
//public interface LoginApi {
//
//    @Operation(summary = "Get authorization token", description = "", tags={ "Login" })
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = InlineResponse200.class))),
//
//        @ApiResponse(responseCode = "400", description = "Invalid input"),
//
//        @ApiResponse(responseCode = "401", description = "Unauthorized") })
//    @RequestMapping(value = "/login",
//        produces = { "application/json" },
//        consumes = { "application/json" },
//        method = RequestMethod.POST)
//    ResponseEntity<InlineResponse200> login(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Body body);
//
//}

/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.25).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.InlineResponse200;
import io.swagger.model.UserLogin;
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
public interface LoginApi {

    @Operation(summary = "Get authorization token", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "Login" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = InlineResponse200.class))),

            @ApiResponse(responseCode = "400", description = "Invalid input"),

            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @RequestMapping(value = "/login",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<InlineResponse200> login(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserLogin body);

}
