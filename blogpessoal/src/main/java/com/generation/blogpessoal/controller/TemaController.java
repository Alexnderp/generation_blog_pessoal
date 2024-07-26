package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Theme", description = "API Theme Controller")
public class TemaController {

    @Autowired
    private TemaRepository temaRepository;

    @Operation(summary = "Get all theme",
            description = "This route returns all themes",
            tags = {"themes", "get"})
    @GetMapping
    public ResponseEntity<List<Tema>> getAll(){
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @Operation(summary = "Get theme by ID",
    description = "This route returns a specific theme based on the id provided",
    tags = {"get"})
    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable UUID id){
        return temaRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Get themes by description",
            description = "This route returns all themes that contain the entered argument",
            tags = {"get"})
    @GetMapping("/description/{description}")
    public ResponseEntity<List<Tema>> getByTitle(@PathVariable
                                                 String description){
        return ResponseEntity.ok(temaRepository
                .findAllByDescriptionContainingIgnoreCase(description));
    }
    @Operation(summary = "Create a new theme",
            description = "This route creates a new theme",
            tags = {"post"})
    @PostMapping
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema theme){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temaRepository.save(theme));
    }
    @Operation(summary = "Update theme",
            description = "This route updates a specific theme",
            tags = {"put"})
    @PutMapping("/{id}")
    public ResponseEntity<Tema> put(@PathVariable UUID id, @Valid @RequestBody Tema theme){
        theme.setId(id);
        return temaRepository.findById(id)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(temaRepository.save(theme)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(summary = "Delete theme",
            description = "This route deletes a specific theme",
            tags = {"delete"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Optional<Tema> theme = temaRepository.findById(id);

        if(theme.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        temaRepository.deleteById(id);
    }

}
