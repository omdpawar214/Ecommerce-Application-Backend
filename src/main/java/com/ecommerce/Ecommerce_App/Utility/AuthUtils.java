package com.ecommerce.Ecommerce_App.Utility;

import com.ecommerce.Ecommerce_App.Model.User;
import com.ecommerce.Ecommerce_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    @Autowired
    private UserRepository userRepository;

    //get email from the current logged in user
    public String loggedInEmail(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(()->
                new UsernameNotFoundException("the user not found with user name -" + authentication.getName()));
        return user.getEmail();
    }

    //get the userId of current logged in user
    public Long loggedInUserId(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(()->
                new UsernameNotFoundException("the user not found with user name -" + authentication.getName()));
        return user.getUser_Id();
    }

    // get current logged in user
    public User loggedInUser(){
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName()).orElseThrow(()->
                new UsernameNotFoundException("the user not found with user name -" + authentication.getName()));
        return user;
    }
}
