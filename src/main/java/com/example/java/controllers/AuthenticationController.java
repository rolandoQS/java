package com.example.java.controllers;

import com.example.java.Request.CreateUserRequest;
import com.example.java.Request.LoginRequest;
import com.example.java.config.TokenProvider;
import com.example.java.mappers.UserMapper;
import com.example.java.models.User;
import com.example.java.services.UsersService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("auth")
@Api(value = "Auth", tags = { "Auth" })
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;

    private final UsersService usersService;

    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UsersService usersService, TokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.usersService = usersService;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User user = usersService.loginUpdate(token,loginUser.getEmail());
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserResponse(user));
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody CreateUserRequest registerRequest){
        User user = usersService.create(registerRequest);
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        user = usersService.loginUpdate(token,user.getEmail());
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponse(user), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity<?> validate(@RequestParam String token, String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new ResponseEntity<>(tokenProvider.validateToken(token,userDetails), HttpStatus.OK);
    }



}
