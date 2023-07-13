package com.travelagency.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name can't be empty")
    private String firstname;

    @NotBlank(message = "Last name can't be empty")
    private String lastname;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email is not correct")
    @Column(unique = true)
    private String email;
}


