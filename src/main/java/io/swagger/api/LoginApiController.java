package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dtos.TokenDTO;
import io.swagger.model.User;
import io.swagger.model.UserLogin;
import io.swagger.repository.UserRepository;
import io.swagger.service.LoginService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
public class LoginApiController implements LoginApi {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(LoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public LoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    /**
     * Login method for user
     * @param body
     * @return token
     */
    public ResponseEntity<TokenDTO> login(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UserLogin body) {

        try {
            User user = userRepository.findUserByEmail(body.getEmailAddress());
            String token = loginService.login(body.getEmailAddress(), body.getPassword());

            TokenDTO tokenDTO =new TokenDTO();
            tokenDTO.setToken(token);
            return new ResponseEntity<TokenDTO>(tokenDTO,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<TokenDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}