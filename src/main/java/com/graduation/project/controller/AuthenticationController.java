package com.graduation.project.controller;

import com.graduation.project.model.entities.authentication.AuthenticationRequest;
import com.graduation.project.model.entities.authentication.AuthenticationResponse;
import com.graduation.project.service.impl.CoordinatorDetailsServiceImpl;
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

    private final CoordinatorDetailsServiceImpl coordinatorDetailsServiceImpl;

    private final JwtUtil jwtUtil;

    public AuthenticationController(CoordinatorDetailsServiceImpl coordinatorDetailsServiceImpl, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.coordinatorDetailsServiceImpl = coordinatorDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
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

        UserDetails userDetails = coordinatorDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
