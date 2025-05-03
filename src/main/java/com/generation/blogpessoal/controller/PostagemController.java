package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;
import dev.langchain4j.model.openai.OpenAiChatModel;
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
@RequestMapping("/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Posts", description = "API posts controller")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;
    @Autowired
    private TemaRepository temaRepository;

    OpenAiChatModel assistant = OpenAiChatModel.builder()
            .baseUrl("http://langchain4j.dev/demo/openai/v1")
            .apiKey("demo")
            .modelName("gpt-4o-mini")
            .build();

    @Operation(summary = "Get all posts",
            description = "This route returns all posts",
            tags = {"posts", "get"})
    @GetMapping
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @Operation(summary = "Get post by ID",
            description = "This route returns a specific post based on the id provided",
            tags = {"posts", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable UUID id) {
        return postagemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get posts by name",
            description = "This route returns all posts that contain the entered argument",
            tags = {"posts", "get"})
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Postagem>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(postagemRepository.findAllByTitleContainingIgnoreCase(title));
    }

    @Operation(summary = "Create a new post",
            description = "This route creates a new post",
            tags = {"post"})
    @PostMapping
    public ResponseEntity<Postagem> create(@Valid @RequestBody Postagem postagem) {
        if (temaRepository.existsById(postagem.getTema().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postagemRepository.save(postagem));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado", null);
    }

    @PostMapping("/ia")
    public String generatePostByIa(@RequestBody String title, Tema theme) {
        String prompt = String.format("Escreva um texto para um blog tecnológico com temática de detetive sobre %s focando em %s como assunto geral e com no máximo 100 caracteres", theme.getDescription(), title);
        if (!title.isEmpty() && !theme.getDescription().isEmpty())
            return assistant.chat(prompt) ;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao gerar postagem", null);

    }

    @Operation(summary = "Update post",
            description = "This route update a specific post based on the id",
            tags = {"update"})
    @PutMapping("/{id}")
    public ResponseEntity<Postagem> update(@PathVariable UUID id, @Valid @RequestBody Postagem postagem) {
        if (postagemRepository.existsById(id)) {
            postagem.setId(id);
            if (temaRepository.existsById(postagem.getTema().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(postagemRepository.save(postagem));

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não encontrado", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete post",
            description = "This route deletes a specific post based on the id",
            tags = {"delete"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Optional<Postagem> postagem = postagemRepository.findById(id);

        if (postagem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        postagemRepository.deleteById(id);
    }
}
