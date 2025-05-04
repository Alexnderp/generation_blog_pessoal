package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotBlank(message = "Nome é um campo obrigtório")
    private String name;
    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Favor inserir um email válido")
    private String email;
    @Size(min = 6)
    @NotNull(message = "Senha é um campo obrigatório")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String photo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("usuario")
    private List<Postagem> posts;

    public Usuario( String id, String name, String email, String password, String photo, List<Postagem> posts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.posts = posts;


    }
}
