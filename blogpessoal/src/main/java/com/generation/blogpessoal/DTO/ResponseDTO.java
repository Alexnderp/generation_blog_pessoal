package com.generation.blogpessoal.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ResponseDTO {
    private String email;
    private String name;
    private String Token;
}
