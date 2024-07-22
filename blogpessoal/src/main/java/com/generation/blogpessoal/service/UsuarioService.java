package com.generation.blogpessoal.service;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Usuario> update(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            Optional<Usuario> searchUser = usuarioRepository.findByEmail(usuario.getEmail());

            if ((searchUser.isPresent()) && (searchUser.get().getId()) != usuario.getId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario inválido", null);

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return Optional.ofNullable(usuarioRepository.save(usuario));

        }

        return Optional.empty();
    }
}
