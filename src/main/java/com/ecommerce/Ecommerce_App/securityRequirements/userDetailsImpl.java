package com.ecommerce.Ecommerce_App.securityRequirements;

import com.ecommerce.Ecommerce_App.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class userDetailsImpl implements UserDetails {
    private static final Long serialVersionId =1L;

    //field required
    private Long userId;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    //required constructors
    public userDetailsImpl(Long userId, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    //method to build/initialize our own custom user details class
    public static userDetailsImpl build(User user){
        List<GrantedAuthority> authorities =  user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
        return new userDetailsImpl(
                user.getUser_Id(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities

        );
    }

    // predefined methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || getClass() !=o.getClass()) return false;
        userDetailsImpl userDetails = (userDetailsImpl) o;
        return Objects.equals(userId,userDetails.userId);
    }
}
