package io.swagger.api;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.ModifyUserDTO;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.UserDTO;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping(value="/users")
public class UsersApiController implements UsersApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;

    }

    @RequestMapping(value="",
            method = RequestMethod.POST ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "User registered", required=true, schema=@Schema()) @Valid @RequestBody User user)
    {
        try {
            if(userService.createUser(user) == null){
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST).status(HttpStatus.BAD_REQUEST).body(null);}

            userService.createUser(user);
            return new ResponseEntity<User>(HttpStatus.CREATED).status(201).body(user);

        } catch(Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value="",
            method = RequestMethod.DELETE ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId) {

        try {
            userService.deleteUserById(userId);
            return new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value="/{userId}",
            method = RequestMethod.GET ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId) throws Exception {

        try {

            User user = userService.getUserById(userId);
            return new ResponseEntity<User>(HttpStatus.FOUND).status(200).body(user);

        } catch (Exception e ){

            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value="",
            method = RequestMethod.GET ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<User> users = userService.getUsers(offset, limit);
//        List<UserDTO> publicUsers = new ArrayList<>();
//
//        for (User user: users) {
//            publicUsers.add(convertToUserDTO(user));
//        }
        return new ResponseEntity<List<User>>(HttpStatus.ACCEPTED).status(200).body(users);

//        return new ResponseEntity<List<UserDTO>>(publicUsers,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value="/{userId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId, @Valid @RequestBody User body) {

        try {
            userService.updateUser(body,userId);
            return new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e){

            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
