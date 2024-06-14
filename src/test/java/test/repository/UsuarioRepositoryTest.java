package test.repository;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.testContainers.AbstractIntegrationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @Test
    void findByEmail() {
        usuario = Usuario.builder()
                .email("email@email.com")
                .nome("nome")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("password")
                .roles(List.of(Role.builder().name(RoleName.ROLE_ADMINISTRATOR).build()))
                .build();

    }

    @DisplayName("JUnit test salvar um usuario")
    @Test
    void salvar() {
        // Given / Arrange

        // When / Act
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Then / Assert
        assertNotNull(savedUsuario);
        assertTrue(savedUsuario.getId() > 0);
    }
}