package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.DTO.QueryResponseDTO;
import com.generation.blogpessoal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u.name, u.email, u.photo, u.posts FROM Usuario u")
    List<QueryResponseDTO> findUsers();
}

