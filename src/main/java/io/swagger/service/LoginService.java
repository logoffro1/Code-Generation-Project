package io.swagger.service;

import io.swagger.model.User;
import lombok.extern.java.Log;
import io.swagger.repository.UserRepository;
import io.swagger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Log
public class LoginService {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    public String login(String email, String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, List.of(userRepository.findUserByEmail(email).getRole()));
        } catch (AuthenticationException ae) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid credentials");
        }
    }

    public String add(String email, String password, List<User.RoleEnum> roles) {
        if (userRepository.findUserByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder().encode(password));
            user.setRole(User.RoleEnum.ROLE_EMPLOYEE);

            log.info(user.toString());
            userRepository.save(user);
            String token = jwtTokenProvider.createToken(user.getEmail(), List.of(user.getRole()));
            return token;
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already in use");
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
