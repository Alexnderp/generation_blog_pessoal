package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Tema;
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
@RequestMapping("/themes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

    @Autowired
    private TemaRepository temaRepository;

    @GetMapping
    public ResponseEntity<List<Tema>> getAll(){
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable UUID id){
        return temaRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Tema>> getByTitle(@PathVariable
                                                 String description){
        return ResponseEntity.ok(temaRepository
                .findAllByDescriptionContainingIgnoreCase(description));
    }

    @PostMapping
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema theme){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temaRepository.save(theme));
    }

    @PutMapping
    public ResponseEntity<Tema> put(@PathVariable UUID id, @Valid @RequestBody Tema theme){
        theme.setId(id);
        return temaRepository.findById(id)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(temaRepository.save(theme)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Optional<Tema> theme = temaRepository.findById(id);

        if(theme.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        temaRepository.deleteById(id);
    }

}
