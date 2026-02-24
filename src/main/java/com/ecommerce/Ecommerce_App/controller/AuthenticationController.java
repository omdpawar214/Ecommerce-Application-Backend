package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.MessageResponse;
import com.ecommerce.Ecommerce_App.DTOs.SignUpRequest;
import com.ecommerce.Ecommerce_App.Model.Role;
import com.ecommerce.Ecommerce_App.Model.User;
import com.ecommerce.Ecommerce_App.Model.UsersRoles;
import com.ecommerce.Ecommerce_App.jwtSecurity.JWTUtils;
import com.ecommerce.Ecommerce_App.DTOs.LoginRequest;
import com.ecommerce.Ecommerce_App.DTOs.LoginResponse;
import com.ecommerce.Ecommerce_App.repository.RoleRepository;
import com.ecommerce.Ecommerce_App.repository.UserRepository;
import com.ecommerce.Ecommerce_App.securityRequirements.userDetailsImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
 import com.ecommerce.Ecommerce_App.Configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    //endpoint for sign-in
    @PostMapping("/signIn")
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

    //endpoint for sign-Up
    @PostMapping("/signUp")
    public ResponseEntity<?> signUpTheUser( @Valid @RequestBody SignUpRequest signUpRequest){
      //if user are already exists
        if (userRepository.existsByUserName(signUpRequest.getUserName()) || userRepository.existsByEmail(signUpRequest.getEmail())){
          return new ResponseEntity<>(new MessageResponse("User Already Exists") , HttpStatus.CONFLICT);
      }
    User newUser = new User(signUpRequest.getUserName(),signUpRequest.getEmail(),passwordEncoder.encode(signUpRequest.getPassword()));
        List<String> StrRoles = signUpRequest.getRoles();
       List<Role> roles = new ArrayList<>();

       if(roles == null){
          Role role= roleRepository.findByRole(UsersRoles.ROLE_USER);
          roles.add(role);
       }else {
           StrRoles.forEach(role-> {
               switch (role) {
                   case "admin":
                       Role Adminrole = roleRepository.findByRole(UsersRoles.ROLE_ADMIN);
                       roles.add(Adminrole);
                       break;
                   case "seller":
                       Role sellerrole = roleRepository.findByRole(UsersRoles.ROLE_SELLER);
                       roles.add(sellerrole);
                       break;
                   default:
                       Role Userrole = roleRepository.findByRole(UsersRoles.ROLE_USER);
                       roles.add(Userrole);

               }
           } );
       }
       newUser.setUserRoles(roles);
       userRepository.save(newUser);
       return new ResponseEntity<>(new MessageResponse("New User Added Successfully"),HttpStatus.OK);
    }
}
