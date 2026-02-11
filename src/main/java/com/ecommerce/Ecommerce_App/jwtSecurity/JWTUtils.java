package com.ecommerce.Ecommerce_App.jwtSecurity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {
    //see the more precise logs
    private static final Logger loger = LoggerFactory.getLogger(JWTUtils.class);


    @Value("${spring.app.ExpirationPeriodMs}")
     private int ExpirationPeriodMs;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    //extract jwt from headers
    public String getJWTFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        loger.debug("Authorization Header {}",bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    //generating token from username
    public String generateTokenFromUserName(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+ ExpirationPeriodMs)))
                .signWith(key())
                .compact();
    }
    //getting username from jwt token
    public String usernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    //generate sign-in key
    public Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //validate jwt token
    public boolean validate(String authToken ){
        try{
            System.out.println("validate");
            Jwts.parser()
                    .verifyWith( (SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }catch (Exception e){
            loger.error("Validation Failed :: "+ e.getMessage());
        }
        return false;
    }




}
