package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostagemRepository extends JpaRepository<Postagem, UUID> {

    public List<Postagem> findAllByTitleContainingIgnoreCase(@Param("title") String title);
}
