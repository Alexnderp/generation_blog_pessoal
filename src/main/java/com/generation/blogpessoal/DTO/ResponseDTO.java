package com.generation.blogpessoal.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ResponseDTO {
    private String id;
    private String email;
    private String name;
    private String photo;
    private String Token;
}
