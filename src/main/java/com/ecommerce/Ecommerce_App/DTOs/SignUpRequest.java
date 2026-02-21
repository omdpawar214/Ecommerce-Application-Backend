package com.ecommerce.Ecommerce_App.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor

public class SignUpRequest {
    @NotBlank
    @Size(min = 2 ,message = "this Field must Contains at-least 2 characters")
    private String UserName;
    @Email
    private String email;
    private String password;
    private List<String> roles;

}
