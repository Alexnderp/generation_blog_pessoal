package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.DTO.LoginDTO;
import com.generation.blogpessoal.DTO.ResponseDTO;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.security.TokenService;
import com.generation.blogpessoal.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Auth", description = "API security controller")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Operation(summary = "Login to API",
            description = "This route logs into the API",
            tags = {"post"})
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO login) {
        Usuario usuario = usuarioRepository.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        if (passwordEncoder.matches(login.getPassword(), usuario.getPassword())) {
            String token = tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getEmail(), usuario.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
    @Operation(summary = "Register to API",
            description = "This route registers a new user in the API",
            tags = {"post"})
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Usuario usuario, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Usuario> searchUser = this.usuarioRepository.findByEmail(usuario.getEmail());
            if (searchUser.isEmpty()) {
                String photo = cloudinaryService.uploadImage(file);
                Usuario newUser = new Usuario();
                newUser.setName(usuario.getName());
                newUser.setEmail(usuario.getEmail());
                newUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
                newUser.setPhoto(photo);
                String token = this.tokenService.generateToken(usuario);

                this.usuarioRepository.save(newUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(usuario.getEmail(), usuario.getName(), token));
            }

        }catch (IOException e){
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.badRequest().build();
    }
}
