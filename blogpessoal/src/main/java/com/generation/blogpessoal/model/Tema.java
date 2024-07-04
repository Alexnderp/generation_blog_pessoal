package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "temas")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O campo descrição é obrigatório")
    private String description;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "tema", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("tema")
    private List<Postagem> post;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Postagem> getPost() {
        return post;
    }

    public void setPost(List<Postagem> post) {
        this.post = post;
    }
}
