package com.generation.blogpessoal.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO{
    private String email;
    private String password;
}