package com.generation.blogpessoal.controller;


import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthController authController;

    @BeforeAll
    void start(){
        usuarioRepository.deleteAll();

        authController.register(new Usuario("00001","Root","root@root.com","rootroot", " "));
    }

    @Test
    @DisplayName("Register")
    public void createUser(){
        HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(new Usuario("1234","Alex","alex@alex.com","12345678","-"));

        ResponseEntity<Usuario> responseBody = testRestTemplate.exchange("/auth/register", POST, requestBody, Usuario.class);

        assertEquals(HttpStatus.CREATED, responseBody.getStatusCode());
    }

    @Test
    @DisplayName("Not Duplicate")
    public void notDuplicate(){
        authController.register(new Usuario("1222","ze","ze@ze.com","rootroot", " "));

        HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(new Usuario("1254","ze","ze@ze.com","rootroot", " "));
        ResponseEntity<Usuario> responseBody = testRestTemplate.exchange("/auth/register", POST, requestBody, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseBody.getStatusCode());

    }

    @Test
    @DisplayName("Update User")
    public void updateUser() throws Exception{

        ResponseEntity<Usuario> registeredUser = authController.register(new Usuario("12554","clara","clara@clara.com","rootroot", " "));

        Usuario updateUser = new Usuario(registeredUser.getBody().getId(),"Kiara","clara@clara.com","rootroot", " ");

        HttpEntity<Usuario> requestBody = new HttpEntity<Usuario>(updateUser);

        ResponseEntity<String> responseBody = testRestTemplate
                .withBasicAuth("root@root.com","rootroot")
                .exchange("/usuarios", PUT, requestBody,String.class);

        assertEquals(HttpStatus.OK, responseBody.getStatusCode());

    }

    @Test
    @DisplayName("List all users")
    public void listAllUsers() throws Exception{
        authController.register(new Usuario("122445","ze","ze@ze.com","1234567", " "));
        authController.register(new Usuario("144435","marianne","mari@mari.com","13247658", " "));

        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios", GET, null,String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
