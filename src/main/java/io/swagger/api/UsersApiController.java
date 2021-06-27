package io.swagger.api;

import io.swagger.exceptions.ApiRequestException;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dtos.CreateUserDTO;
import io.swagger.model.dtos.ModifyUserDTO;
import io.swagger.model.dtos.ResponseUserDTO;
import io.swagger.model.dtos.UserDTO;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.coyote.Response;
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

    /**
     * Creates a user, authorized only for employee
     * @param userDTO
     * @return created user
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="",
            method = RequestMethod.POST ,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserDTO> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "User registered", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDTO userDTO) {

        try {
            userService.createUser(convertFromCreateUserDtoToUser(userDTO));
            return new ResponseEntity<CreateUserDTO>(HttpStatus.CREATED).status(201).body(userDTO);

        } catch (Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Deletes a user, authorized only for employee
     * @param userId
     */
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

    /**
     * Gets a users by Id, Employee can get all users, customer can only retrieve himself
     * @param userId
     * @return the user with the specified id
     * @throws Exception
     */
    @RequestMapping(value="/{userId}",
            method = RequestMethod.GET ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUserDTO> getUserById(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId) throws Exception {

        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<ResponseUserDTO>(HttpStatus.FOUND).status(200).body(convertFromUserToResponseUserDTO(user));

        } catch (Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Returns a list of user, authorized only for employees
     * @param offset
     * @param limit
     * @return list of users
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="",
            method = RequestMethod.GET ,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseUserDTO>> getUsers(@Parameter(in = ParameterIn.QUERY, description = "The number of pages to skip before starting to collect the query results" ,schema=@Schema()) @Valid @RequestParam(value = "offset", required = false) Integer offset, @Parameter(in = ParameterIn.QUERY, description = "The numbers of users to return per page" ,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        //convertying the user to user dto before displaying
        try {
            List<UserDTO> publicUsers = new ArrayList<>();
            List<User> users = userService.getUsers(limit, offset);
            for (User user: users) {
                publicUsers.add(convertFromUserToUserDTO(user));
            }
            return new ResponseEntity<List<ResponseUserDTO>>(HttpStatus.ACCEPTED).status(200).body(convertFromUserToResponseUserDTOArray(users));

        } catch (Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Updates a user
     * @param userId
     * @param user
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(value="/{userId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@Min(1L)@Parameter(in = ParameterIn.PATH, description = "User id to get from the database", required=true, schema=@Schema(allowableValues={  }, minimum="1"
)) @PathVariable("userId") Long userId, @Valid @RequestBody ModifyUserDTO user) {

        try {
            userService.updateUser(user,userId);
            return new ResponseEntity<Void>(HttpStatus.OK);

        } catch (Exception e) {
            throw new ApiRequestException("Something went wrong!",HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Converts a user to userDTO
     * @param user
     * @return converted user
     */
    private UserDTO convertFromUserToUserDTO(User user) {
        if(user != null)
            return new UserDTO(user.getFirstName(),user.getLastName(),user.getPhoneNumber() ,user.getEmail(), Arrays.asList(user.getRole().values()));
        else
            return null;
    }

    /**
     * Converts a createUser Dto to user
     * @param userDTO
     * @return converted user
     */
    private User convertFromCreateUserDtoToUser(CreateUserDTO userDTO){
        if(userDTO != null)
            return new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmailAddress(),userDTO.getPassword(),userDTO.getPhoneNumber(),userDTO.getRole() );
        else
            return null;
    }

    private ResponseUserDTO convertFromUserToResponseUserDTO(User user){
        return new ResponseUserDTO(user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getEmail(),user.getRole());
    }

    private List<ResponseUserDTO> convertFromUserToResponseUserDTOArray (List<User> users){
        List<ResponseUserDTO> responseUserDTOS= new ArrayList<>();
        for(User u :users){
            responseUserDTOS.add(convertFromUserToResponseUserDTO(u));
        }
        return responseUserDTOS;
    }

    /**
     * Converts a ModifyUserDto to user
     * @param userDTO
     * @return converted user
     */
    private User convertFromModifyUserDtoToUser(ModifyUserDTO userDTO){
        if(userDTO != null)
            return new User(userDTO.getFirstName(),userDTO.getLastName(), userDTO.getEmailAddress(),userDTO.getPassword(),userDTO.getPhoneNumber());
        else
            return null;
    }
}
