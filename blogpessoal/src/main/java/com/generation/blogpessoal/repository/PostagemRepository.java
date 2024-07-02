package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostagemRepository extends JpaRepository<Postagem, UUID> {
}
