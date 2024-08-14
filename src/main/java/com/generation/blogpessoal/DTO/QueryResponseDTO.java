package com.generation.blogpessoal.DTO;

import com.generation.blogpessoal.model.Postagem;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@NoArgsConstructor
public class QueryResponseDTO {
    private String name;
    private String email;
    private String photo;
    private List<Postagem> posts;

}
