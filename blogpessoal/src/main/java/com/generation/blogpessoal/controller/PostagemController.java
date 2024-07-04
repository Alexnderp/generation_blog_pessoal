package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private TemaRepository temaRepository;

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

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Postagem>> getByTitle(@PathVariable String title){
        return ResponseEntity.ok(postagemRepository.findAllByTitleContainingIgnoreCase(title));
    }

    @PostMapping
    public ResponseEntity<Postagem> create(@Valid @RequestBody Postagem postagem){
        if (temaRepository.existsById(postagem.getTema().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemRepository.save(postagem));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado", null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> update (@PathVariable UUID id, @Valid @RequestBody Postagem postagem){
        if (postagemRepository.existsById(id)){
            postagem.setId(id);
            if (temaRepository.existsById(postagem.getTema().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(postagemRepository.save(postagem));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable UUID id){
        Optional<Postagem> postagem = postagemRepository.findById(id);

        if (postagem.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        postagemRepository.deleteById(id);
    }
}
