package br.com.template.repository;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import testContainers.AbstractIntegrationTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UsuarioRepository repository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .email("andre@email.com")
                .nome("Andre da Silva")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("andre123456")
                .role(Role.builder()
                        .name(RoleName.ROLE_ADMINISTRATOR)
                        .build())
                .build();

    }

    @DisplayName("JUnit test salvar um usuario")
    @Test
    void salvar() {
        Usuario savedUsuario = repository.save(usuario);

        assertNotNull(savedUsuario);
        assertTrue(savedUsuario.getId() > 0);
    }

    @DisplayName("JUnit test recuperar uma lista de usuarios")
    @Test
    void findAll() {
        repository.save(usuario);
        repository.save(Usuario.builder()
                .email("paulo@email.com")
                .nome("Paulo de Souza")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("paulo987654")
                .role(Role.builder()
                        .name(RoleName.ROLE_CUSTOMER)
                        .build())
                .build());

        List<Usuario> usuarios = repository.findAll();

        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @DisplayName("JUnit test recuperar um usuario por um ID fornecido")
    @Test
    void findID() {
        Usuario usuarioSavad = repository.save(usuario);
        repository.save(Usuario.builder()
                .email("paulo@email.com")
                .nome("Paulo de Souza")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("paulo987654")
                .role(Role.builder()
                        .name(RoleName.ROLE_CUSTOMER)
                        .build())
                .build());

        Integer expected = usuarioSavad.getId();

        Optional<Usuario> locatedUsuario = repository.findById(usuario.getId());

        assertTrue(locatedUsuario.isPresent());
        assertEquals(expected, locatedUsuario.get().getId());
        assertEquals(RoleName.ROLE_ADMINISTRATOR, locatedUsuario.get().getRole().getName());
        assertEquals(usuario.getEmail(), locatedUsuario.get().getEmail());
    }

    @DisplayName("JUnit test update um usuario existente")
    @Test
    void update() {
        Usuario savedUsuario = repository.save(usuario);

        Usuario locatedUsuario = repository.findById(savedUsuario.getId()).get();
        locatedUsuario.setEmail("paulo@email.com");
        locatedUsuario.setRole(Role.builder()
                .name(RoleName.ROLE_CUSTOMER)
                .build());


        Usuario updatedUsuario = repository.save(locatedUsuario);

        assertNotNull(updatedUsuario);
        assertEquals("paulo@email.com", updatedUsuario.getEmail());
        assertEquals(RoleName.ROLE_CUSTOMER, updatedUsuario.getRole().getName());
    }

    @DisplayName("JUnit test delete um usuario existente")
    @Test
    void delete() {
        Usuario usuarioSavad = repository.save(usuario);

        Usuario locatedUsuario = repository.findById(usuarioSavad.getId()).get();

        repository.delete(locatedUsuario);

        Optional<Usuario> optionalUsuario = repository.findById(usuario.getId());

        assertTrue(optionalUsuario.isEmpty());
    }

    @DisplayName("JUnit test recuperar um usuario por um e-mail fornecido")
    @Test
    void findByEmail() {
        Usuario usuarioSavad = repository.save(usuario);
        repository.save(Usuario.builder()
                .email("paulo@email.com")
                .nome("Paulo de Souza")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("paulo987654")
                .role(Role.builder()
                        .name(RoleName.ROLE_CUSTOMER)
                        .build())
                .build());

        Integer expected = usuarioSavad.getId();

        Optional<Usuario> locatedUsuario = repository.findByEmail(usuario.getEmail());

        assertTrue(locatedUsuario.isPresent());
        assertEquals(expected, locatedUsuario.get().getId());
        assertEquals(RoleName.ROLE_ADMINISTRATOR, locatedUsuario.get().getRole().getName());
        assertEquals(usuario.getEmail(), locatedUsuario.get().getEmail());

    }
}