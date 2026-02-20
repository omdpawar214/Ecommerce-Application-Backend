package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.jwtSecurity.JWTUtils;
import com.ecommerce.Ecommerce_App.jwtSecurity.LoginRequest;
import com.ecommerce.Ecommerce_App.jwtSecurity.LoginResponse;
import com.ecommerce.Ecommerce_App.securityRequirements.userDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    //endpoint for sign-in
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),loginRequest.getPassword()
                    ));
        }catch (AuthenticationException e){
            Map<String,Object> map = new HashMap<>();
            map.put("message" ,"Bad Credentials");
            map.put("status" , false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userDetailsImpl userDetails = (userDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUserName(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUserId(),userDetails.getUsername(),roles,jwtToken);

        return new ResponseEntity<>(response ,HttpStatus.ACCEPTED);
    }
}
