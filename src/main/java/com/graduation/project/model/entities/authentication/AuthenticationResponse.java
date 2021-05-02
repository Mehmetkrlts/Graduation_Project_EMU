package com.graduation.project.model.entities.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Login succesfull olduğunda tokeni döner.
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        super();
        this.token = token;
    }
}
