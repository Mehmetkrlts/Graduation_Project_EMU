package com.graduation.project.utility;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUtil {

    private String secret;
    private int jwtExpirationInMs;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    //TODO  suanda 5 saattir ilerde değitirilecek applicationpropertiesin içinden.
    @Value("${jwt.jwtExpirationInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        //TODO TOKENİN İÇİNE ROLLERİ EKLER DAHA SONRA DİĞER ROLLER EKLENECEK
        if(roles.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"))){
            claims.put("isCoordinator", true);
        }
        if(roles.contains(new SimpleGrantedAuthority("ROLE_STUDENT"))){
            claims.put("isStudent", true);
        }
        if(roles.contains(new SimpleGrantedAuthority("ROLE_JURY"))){
            claims.put("isJury", true);
        }

        return doGenerateToken(claims,userDetails.getUsername());

    }

    // Tokeni oluşturur.
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public boolean validateToken(String authToken){
        try{
            Jws<Claims> claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        }catch(SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex){
            throw new BadCredentialsException("INVALID_CREDENTIALS",ex);
        }
        catch(ExpiredJwtException ex){
            throw ex;
        }
    }

    public String getUsernameFromToken(String token){
        Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token){
        Claims claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List<SimpleGrantedAuthority> roles = null;

        Boolean isCoordinator = claims.get("isCoordinator",Boolean.class);
        Boolean isStudent = claims.get("isStudent",Boolean.class);
        Boolean isJury = claims.get("isJury",Boolean.class);

        if(isCoordinator != null && isCoordinator){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        }

        if(isStudent != null && isStudent){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        if(isJury != null && isJury){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_JURY"));
        }

        return roles;

    }
}
