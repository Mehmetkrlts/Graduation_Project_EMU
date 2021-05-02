package com.graduation.project.model.entities.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }


}