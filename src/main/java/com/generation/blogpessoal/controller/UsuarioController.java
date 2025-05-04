package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.DTO.QueryResponseDTO;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.CloudinaryService;
import com.generation.blogpessoal.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Get all users",
            description = "This route returns all users",
            tags = {"get"})
    @GetMapping()
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }


    @Operation(summary = "Get user by ID",
            description = "This route returns a specific user based on the id provided",
            tags = {"get"})
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update user",
            description = "This route update a specific post based on the id",
            tags = {"put"})
    @PutMapping
    public ResponseEntity<Usuario> update(@Valid @RequestParam("id") String id,
                                          @RequestParam("email") String email,
                                          @RequestParam("name") String name,
                                          @RequestParam("password") String password,
                                          @RequestPart("photo") MultipartFile file) {

        try {
            Usuario usuario = new Usuario(id, name, email, password, null, null);
            String photo = cloudinaryService.uploadImage(file);
            usuario.setPhoto(photo);
            return usuarioService.update(usuario)
                    .map(response -> ResponseEntity.ok().body(response))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
