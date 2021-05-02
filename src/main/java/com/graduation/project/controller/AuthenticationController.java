package com.graduation.project.controller;

import com.graduation.project.model.dto.UserDTO;
import com.graduation.project.model.entities.authentication.AuthenticationRequest;
import com.graduation.project.model.entities.authentication.AuthenticationResponse;
import com.graduation.project.service.CustomUserDetailsService;
import com.graduation.project.utility.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public AuthenticationController(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        userDetailsService.save(user);
        return ResponseEntity.ok("User registered");
    }

    //TODO ilerde bunu /logine çevirebilirik.
    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                            ,authenticationRequest.getPassword())
            );
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }
        catch(BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
