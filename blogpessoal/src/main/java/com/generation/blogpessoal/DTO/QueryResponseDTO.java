package com.generation.blogpessoal.DTO;

import com.generation.blogpessoal.model.Postagem;

import java.util.List;

public record QueryResponseDTO(String name, String email, String photo, List<Postagem> posts) {
}
