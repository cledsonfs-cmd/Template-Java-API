package br.com.template.controller;

import br.com.template.enums.RoleName;
import br.com.template.enums.UsuarioStates;
import br.com.template.model.UserDetailsImpl;
import br.com.template.model.dto.*;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import br.com.template.security.service.JwtTokenService;
import br.com.template.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import security.SecurityConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)//, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(SecurityConfig.class)
class UserControllerTest {

    @MockBean
    private UserServiceImpl mockUserService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtTokenService mockJwtTokenService;
    @MockBean
    private UsuarioRepository mockUsuarioRepository;



    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private UsuarioDTO mockUsuarioDTO;
    private CreateUserDto mockCreateUserDTO;
    private LoginRequestDTO mockLoginRequestDTO;
    private RecoveryJwtTokenDto mockRecoveryJwtTokenDto;
    private UserDetailsImpl mockUserDetails;
    private Usuario mockUsuario;

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
        mockUsuarioDTO = new UsuarioDTO(1976, "andre@email.com", UsuarioStates.ATIVO.getId());
        mockCreateUserDTO = new CreateUserDto("paulo@email.com", "paulo", "paulo123456", UsuarioStates.ATIVO.getId(), RoleName.ROLE_ADMINISTRATOR);
        mockLoginRequestDTO = new LoginRequestDTO("paulo@email.com", "paulo");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
    }

    @DisplayName("JUnit test de login")
    @Test
    void authenticateUser() throws Exception {
        given(mockUserService.authenticateUser(any(LoginRequestDTO.class))).willReturn(mockRecoveryJwtTokenDto);

        ResultActions response = mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockLoginRequestDTO)));

        response.andDo(print())
                //.andExpect(jsonPath("$", is(expectMessage)))
                .andExpect(status().isOk());

    }

    @DisplayName("JUnit test de criação de um usuario")
    @Test
    void createUser() throws Exception {
        String expectMessage = "Usuario criado com sucesso!";
        given(mockUserService.createUser(any(CreateUserDto.class))).willReturn("Usuario criado com sucesso!");

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockCreateUserDTO)));

        response.andDo(print())
                .andExpect(jsonPath("$", is(expectMessage)))
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test de update de um usuario")
    @Test
    void atualizar() throws Exception {
        mockUsuario.setNome("Teste Unitario");

        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.atualizarUser(any(UsuarioDTO.class))).willReturn("Usuario atualizado com sucesso!");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(put("/users/update")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Usuario atualizado com sucesso!")));
    }

    @DisplayName("JUnit test de autenticação")
    @Test
    void getAuthenticationTest() throws Exception {
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(get("/users/test")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        response.
                andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JUnit test de autenticação de usuario com a role customer")
    @Test
    void getCustomerAuthenticationTest() throws Exception {
        mockUsuario.setRole(Role.builder()
                .name(RoleName.ROLE_CUSTOMER)
                .build());
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(get("/users/test/customer")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        response.
                andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("JUnit test de autenticação de usuario com a role administrador")
    @Test
    void getAdminAuthenticationTest() throws Exception {
        mockUsuario.setRole(Role.builder()
                .name(RoleName.ROLE_ADMINISTRATOR)
                .build());
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(get("/users/test/administrator")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));

        response.
                andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JUnit test ativar um usuario cadastrado")
    @Test
    void ativar() throws Exception {
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.ativar(any(UsuarioDTO.class))).willReturn("Usuario Ativado com sucesso!");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(put("/users/ativar")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Usuario Ativado com sucesso!")));
    }

    @DisplayName("JUnit test suspender um usuario cadastrado")
    @Test
    void suspender() throws Exception {
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.suspender(any(UsuarioDTO.class))).willReturn("Usuario Suspenso com sucesso!");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(put("/users/suspender")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Usuario Suspenso com sucesso!")));
    }

    @DisplayName("JUnit test inativar um usuario cadastrado")
    @Test
    void inativar() throws Exception {
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.inativar(any(UsuarioDTO.class))).willReturn("Usuario Inativado com sucesso!");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(put("/users/inativar")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Usuario Inativado com sucesso!")));
    }

    @DisplayName("JUnit test excluir um usuario cadastrado")
    @Test
    void excluir() throws Exception {
        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.excluir(any(UsuarioDTO.class))).willReturn("Usuario Excluído com sucesso!");
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(put("/users/excluir")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", is("Usuario Excluído com sucesso!")));
    }

    @DisplayName("JUnit test retorna uma lista de usuarios cadastrado")
    @Test
    void all() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(mockUsuario);
        usuarios.add(mockUsuario);
        usuarios.add(mockUsuario);

        given(mockJwtTokenService.getSubjectFromToken(anyString())).willReturn(mockUsuario.getEmail());
        given(mockUsuarioRepository.findByEmail(anyString())).willReturn(Optional.ofNullable(mockUsuario));
        given(mockUserService.findAll()).willReturn(usuarios);
        mockUserDetails = new UserDetailsImpl(mockUsuario);
        String token = new JwtTokenService().generateToken(mockUserDetails);

        ResultActions response = mockMvc.perform(get("/users/all")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockUsuarioDTO)));

        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(usuarios.size())));
    }
}