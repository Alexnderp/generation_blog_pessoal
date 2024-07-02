package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    @GetMapping
    public ResponseEntity<List<Postagem>> getAll(){
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable UUID id){
        return postagemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Postagem> create(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemRepository.save(postagem));
    }
}
