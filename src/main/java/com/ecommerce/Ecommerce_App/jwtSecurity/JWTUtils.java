package com.ecommerce.Ecommerce_App.jwtSecurity;

import com.ecommerce.Ecommerce_App.securityRequirements.userDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {
    //see the more precise logs
    private static final Logger loger = LoggerFactory.getLogger(JWTUtils.class);


    @Value("${spring.app.ExpirationPeriodMs}")
     private  int ExpirationPeriodMs;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.MyCookie}")
    private String MyCookie;

    //extract jwt from headers
//    public String getJWTFromHeader(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        loger.debug("Authorization Header {}",bearerToken);
//        if(bearerToken != null && bearerToken.startsWith("Bearer")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }

    //extracting JWT-token from Cookie request
    public String getJwtFromCookie(HttpServletRequest request) {
        System.out.println("jwt token is extracted from cookie");
        Cookie cookie = WebUtils.getCookie(request,MyCookie);
        if(cookie!=null){
            return cookie.getValue();
        }else {
            return null;
        }
    }
    // generating jwt for the valid user and the pack token as cookie
    public ResponseCookie generateJwtCookie(userDetailsImpl userPrincipal){
        System.out.println("Cookie has generated successfully");
        String jwt = generateTokenFromUserName(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(MyCookie,jwt)
                .path("/api")
                .maxAge(24 *60*60)
                .httpOnly(false)
                .build();
        return cookie;
    }

    //generating empty cookie for logout endpoint
    public ResponseCookie generateFreshJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(MyCookie,null)
                .path("/api")
                .build();
        return cookie;
    }

    //generating token from username
    public  String generateTokenFromUserName(String username) {
        System.out.println("jwt token is generated for current user");
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
            System.out.println("validated jwt token for current user");
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
