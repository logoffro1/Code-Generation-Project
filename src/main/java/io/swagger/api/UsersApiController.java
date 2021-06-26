package io.swagger.api;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.util.LoggedInUser;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-26T21:36:39.274Z[GMT]")
@RestController
@RequestMapping(value="/users")
public class UsersApiController implements UsersApi {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {

        this.objectMapper = objectMapper;
        this.request = request;

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="",
            method = RequestMethod.POST ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserDTO> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "User registered", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDTO userDTO)
    {

            if(userDTO == null )
                throw new NullPointerException("User can't be null");

            userService.createUser(convertFromCreateUserDtoToUser(userDTO));
            return new ResponseEntity<CreateUserDTO>(HttpStatus.CREATED).status(201).body(userDTO);

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="/{userId}",
            method = RequestMethod.DELETE )
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

        if(!LoggedInUser.isEmployee())
        {
            if (! userId.equals(LoggedInUser.getUserId()))
            {
                throw new ApiRequestException("You are not allowed to get this user details",HttpStatus.UNAUTHORIZED);
            }
        }
        User user = userService.getUserById(userId);
        return new ResponseEntity<User>(HttpStatus.FOUND).status(200).body(user);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="",
            method = RequestMethod.GET ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of pages to skip before starting to collect the query results" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of users to return per page" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        List<UserDTO> publicUsers = new ArrayList<>();

        try {

            List<User> users = userService.getUsers(limit, offset);
            for (User user: users) {
                publicUsers.add(convertFromUserToUserDTO(user));
            }
            return new ResponseEntity<List<User>>(HttpStatus.ACCEPTED).status(200).body(users);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="/{userId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId, @Valid @RequestBody ModifyUserDTO user) {

        try {
            userService.updateUser(user,userId);
            return new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e){

            return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private UserDTO convertFromUserToUserDTO(User user) {
        return new UserDTO(user.getFirstName(),user.getLastName(),user.getPhoneNumber() ,user.getEmail(), Arrays.asList(user.getRole().values()));
    }

    private User convertFromCreateUserDtoToUser(CreateUserDTO userDTO){
        return new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmailAddress(),userDTO.getPassword(),userDTO.getPhoneNumber(),userDTO.getRole() );
    }

    private User convertFromModifyUserDtoToUser(ModifyUserDTO userDTO){
        return new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmailAddress(),userDTO.getPassword(),userDTO.getPhoneNumber());
    }
}
