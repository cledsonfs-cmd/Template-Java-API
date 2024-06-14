package br.com.template.service.impl;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.dto.LoginRequestDTO;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import br.com.template.security.SecurityConfiguration;
import br.com.template.security.service.JwtTokenService;
import br.com.template.statemachine.service.StateMachineEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenService jwtTokenService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    SecurityConfiguration securityConfiguration;

    @Mock
    StateMachineEventService stateMachineEventService;

    @InjectMocks
    UserServiceImpl userService;

    Usuario mockUsuario;
    LoginRequestDTO loginRequestDTO;

    @BeforeEach
    void setUp() {
        mockUsuario = Usuario.builder()
                .id(1)
                .email("andre@email.com")
                .nome("Andre da Silva")
                .idStatus(UsuarioStates.INICIO.getId())
                .password("andre123456")
                .role(Role.builder()
                        .name(RoleName.ROLE_ADMINISTRATOR)
                        .build())
                .build();

        loginRequestDTO = new LoginRequestDTO("andre@email.com", "andre123456");
    }

    @Test
    void authenticateUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void excluirUser() {
    }

    @Test
    void atualizarUser() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void obterPorEmail() {
    }

    @Test
    void ativar() {
    }

    @Test
    void suspender() {
    }

    @Test
    void inativar() {
    }

    @Test
    void excluir() {
    }
}