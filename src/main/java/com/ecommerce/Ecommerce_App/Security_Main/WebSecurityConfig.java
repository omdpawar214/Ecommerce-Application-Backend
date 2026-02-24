package com.ecommerce.Ecommerce_App.Security_Main;

import com.ecommerce.Ecommerce_App.Model.Role;
import com.ecommerce.Ecommerce_App.Model.User;
import com.ecommerce.Ecommerce_App.Model.UsersRoles;
import com.ecommerce.Ecommerce_App.jwtSecurity.AutEntryPoint;
import com.ecommerce.Ecommerce_App.jwtSecurity.AuthFilterToken;
import com.ecommerce.Ecommerce_App.repository.RoleRepository;
import com.ecommerce.Ecommerce_App.repository.UserRepository;
import com.ecommerce.Ecommerce_App.securityRequirements.userDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class WebSecurityConfig {
    @Autowired
    private userDetailsServiceImpl userDetailsService;
    @Autowired
    private AutEntryPoint unauthorizedUser;

    // To use our own custom filter for jwt authentication
    @Bean
    public AuthFilterToken authFilterToken(){
        return new AuthFilterToken();
    }

    //authentication provider to user our own user details service for authentication
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //adding authentication manager which uses appropriate provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //adding password encoder in our application
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .exceptionHandling(exception ->exception.authenticationEntryPoint(unauthorizedUser))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                //.requestMatchers("/api/public/**").permitAll()
                                //.requestMatchers("/api/admin/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authFilterToken() , UsernamePasswordAuthenticationFilter.class );
        http.headers(header->header.frameOptions(frameOptions->frameOptions.sameOrigin()));
       return http.build();


       //bean to inject some users with their roles
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Retrieve or create roles
            Role userRole = new Role(UsersRoles.ROLE_USER);
            roleRepository.save(userRole);
            Role sellerRole = new Role(UsersRoles.ROLE_SELLER);
            roleRepository.save(sellerRole);
            Role adminRole = new Role(UsersRoles.ROLE_ADMIN);;
            roleRepository.save(adminRole);

            List<Role> userRoles = List.of(userRole);
            List<Role> sellerRoles = List.of(sellerRole);
            List<Role> adminRoles = List.of(userRole, sellerRole, adminRole);


            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setUserRoles(userRoles);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setUserRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setUserRoles(adminRoles);
                userRepository.save(admin);
            });
        };
        }
}
