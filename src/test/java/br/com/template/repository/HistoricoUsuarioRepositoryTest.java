package br.com.template.repository;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import testContainers.AbstractIntegrationTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HistoricoUsuarioRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private HistoricoUsuarioRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioHistorico usuarioHistorico;
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

        usuarioHistorico = UsuarioHistorico.builder()
                .descricao("Teste Unitario")
                .dataRegistro(LocalDateTime.now())
                .build();
    }

    @DisplayName("JUnit test salvar um historico")
    @Test
    void salvar() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        UsuarioHistorico savedUsuarioHistorico = repository.save(usuarioHistorico);

        assertNotNull(savedUsuarioHistorico);
        assertTrue(savedUsuarioHistorico.getId() > 0);
    }

    @DisplayName("JUnit test recuperar uma lista de historicos")
    @Test
    void findAll() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        repository.save(usuarioHistorico);
        repository.save(UsuarioHistorico.builder()
                .descricao("Teste Unitario 2")
                .usuario(usuarioSaved)
                .dataRegistro(LocalDateTime.now())
                .build());

        List<UsuarioHistorico> usuarioHistorico = repository.findAll();

        assertNotNull(usuarioHistorico);
        assertEquals(2, usuarioHistorico.size());
    }

    @DisplayName("JUnit test recuperar um historico por um ID fornecido")
    @Test
    void findID() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        UsuarioHistorico usuarioHistoricoSaved = repository.save(usuarioHistorico);
        repository.save(UsuarioHistorico.builder()
                .descricao("Teste Unitario 2")
                .usuario(usuario)
                .dataRegistro(LocalDateTime.now())
                .build());

        Integer expected = usuarioHistoricoSaved.getId();

        Optional<UsuarioHistorico> locatedUsuarioHistorico = repository.findById(usuarioHistoricoSaved.getId());

        assertTrue(locatedUsuarioHistorico.isPresent());
        assertEquals(expected, locatedUsuarioHistorico.get().getId());
    }

    @DisplayName("JUnit test update um historico existente")
    @Test
    void update() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        UsuarioHistorico usuarioHistoricoSaved = repository.save(usuarioHistorico);

        UsuarioHistorico locatedUsuarioHistorico = repository.findById(usuarioHistoricoSaved.getId()).get();
        locatedUsuarioHistorico.setDescricao("Teste Update");

        UsuarioHistorico updatedUsuarioHistorico = repository.save(locatedUsuarioHistorico);

        assertNotNull(updatedUsuarioHistorico);
        assertEquals("Teste Update", updatedUsuarioHistorico.getDescricao());
    }

    @DisplayName("JUnit test delete um usuario existente")
    @Test
    void delete() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        UsuarioHistorico usuarioHistoricoSaved = repository.save(usuarioHistorico);

        UsuarioHistorico locatedUsuarioHistorico = repository.findById(usuarioHistoricoSaved.getId()).get();

        repository.delete(locatedUsuarioHistorico);

        Optional<UsuarioHistorico> optionalUsuario = repository.findById(usuarioHistoricoSaved.getId());

        assertTrue(optionalUsuario.isEmpty());
    }

    @DisplayName("JUnit test recuperar uma lista de historicos com base em um usuario fornecido")
    @Test
    void findByUsuario() {
        Usuario usuarioSaved = usuarioRepository.save(usuario);
        usuarioHistorico.setUsuario(usuarioSaved);
        repository.save(usuarioHistorico);
        repository.save(UsuarioHistorico.builder()
                .descricao("Teste Unitario 2")
                .usuario(usuarioSaved)
                .dataRegistro(LocalDateTime.now())
                .build());

        List<UsuarioHistorico> usuarioHistorico = repository.findByUsuario(usuarioSaved);

        assertNotNull(usuarioHistorico);
        assertEquals(2, usuarioHistorico.size());
    }
}