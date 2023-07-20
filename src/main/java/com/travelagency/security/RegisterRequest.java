package com.travelagency.security;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;

}
