package com.generation.blogpessoal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "postagens")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Titulo é um campo obrigatório")
    @Size(min = 5, max = 100, message = "O título deve conter no mínimo 5 caracteres")
    private String title;

    @NotBlank(message = "Texto é um campo obrigatório")
    @Size(min = 5, max = 100, message = "Texto deve ter no mínimo 5 e no maximo 100 caracteres")
    private String text;

    @UpdateTimestamp
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
