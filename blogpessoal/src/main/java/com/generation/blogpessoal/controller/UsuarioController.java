package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.DTO.QueryResponseDTO;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<QueryResponseDTO>> getAll(){
        return ResponseEntity.ok(usuarioRepository.findUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable String id){
        return usuarioRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario){

        return usuarioService.update(usuario)
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

}
