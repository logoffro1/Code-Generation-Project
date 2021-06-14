//package io.swagger.api;
//
//import io.swagger.model.Body;
//import io.swagger.model.InlineResponse200;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.model.UserLogin;
//import io.swagger.service.LoginService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.constraints.*;
//import javax.validation.Valid;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
//@RestController
//public class LoginApiController implements LoginApi {
//
//    @Autowired
//    private LoginService loginService;
//
//    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);
//
//    private final ObjectMapper objectMapper;
//
//    private final HttpServletRequest request;
//
//    @org.springframework.beans.factory.annotation.Autowired
//    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
//        this.objectMapper = objectMapper;
//        this.request = request;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserLogin user) {
//       String token = loginService.login(user.getEmailAddress(), user.getPassword());
//      return new ResponseEntity<>(token,HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/add")
//    public ResponseEntity<String> addUser(@RequestBody UserLogin user) {
//
//        String token  = loginService.add(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
//        return ResponseEntity.ok(token);
//    }
//
//    @Override
//    public ResponseEntity<InlineResponse200> login(Body body) {
//        return null;
//    }
//}

package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.InlineResponse200;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserLogin;
import io.swagger.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {
    @Autowired
    private LoginService loginService;

    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<InlineResponse200> login(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserLogin body) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{\n  \"token\" : \"12435-3435465\"\n}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);

              //return new ResponseEntity<>(token,HttpStatus.OK);
        try {
            String token = loginService.login(body.getEmailAddress(), body.getPassword());
            return new ResponseEntity<InlineResponse200>(objectMapper.readValue(token,InlineResponse200.class),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}