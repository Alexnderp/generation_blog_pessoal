package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface TemaRepository extends JpaRepository<Tema, UUID> {
    public List<Tema> findAllByDescriptionContainingIgnoreCase(@Param("description") String description);
}
