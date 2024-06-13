package br.com.template.service.impl;

import br.com.template.enums.UsuarioEvents;
import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioNotFoundException;
import br.com.template.model.UserDetailsImpl;
import br.com.template.model.dto.*;
import br.com.template.model.entity.Role;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import br.com.template.repository.UsuarioRepository;
import br.com.template.security.SecurityConfiguration;
import br.com.template.security.service.JwtTokenService;
import br.com.template.service.UserService;
import br.com.template.statemachine.service.StateMachineEventService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UsuarioRepository usuarioRepository;

    private final SecurityConfiguration securityConfiguration;

    private final StateMachineEventService stateMachineEventService;

    private ModelMapper modelMapper = new ModelMapper();

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginRequestDTO dto) {
        if (dto.email().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (dto.password().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        Optional<Usuario> existe = obterPorEmail(dto.email());
        if (!existe.isPresent()) {
            throw new UsuarioNotFoundException();
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto dto) {

        if (dto.nome().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (dto.email().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (dto.password().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (dto.password().trim().length() < 6) {
            throw new SenhaException();
        }

        if (obterPorEmail(dto.email()).isPresent()) {
            throw new InputMismatchException("Já existe um usuario cadastro com o email: " + dto.email());
        }

        Usuario newUsuarios = Usuario.builder()
                .email(dto.email())
                .nome(dto.nome())
                .idStatus(dto.idstatus())
                .password(securityConfiguration.passwordEncoder().encode(dto.password()))
                .roles(List.of(Role.builder().name(dto.role()).build()))
                .build();

        usuarioRepository.save(newUsuarios);
    }

    @Override
    public Optional<Usuario> obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public String ativar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        UsuarioEvents events = UsuarioEvents.ATIVAR;

        events.setUsuarioHistorico(UsuarioHistorico.builder()
                .descricao("Criação de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        messageHeaderAccessor.setHeader("dto", dto);

        stateMachineEventService.sendEvent(usuario, events, messageHeaderAccessor);

        return "Usuario Ativado com sucesso!";
    }

    @Override
    public String suspender(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioEvents events = UsuarioEvents.SUSPENDER;

        events.setUsuarioHistorico(UsuarioHistorico.builder().descricao("Suspensão de usuário.").build());

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        stateMachineEventService.sendEvent(usuario, events, messageHeaderAccessor);
        return "Usuario Suspenso com sucesso!";
    }

    @Override
    public String inativar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioEvents events = UsuarioEvents.INATIVAR;

        events.setUsuarioHistorico(UsuarioHistorico.builder()
                .descricao("Inativação de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        stateMachineEventService.sendEvent(usuario, events, messageHeaderAccessor);
        return "Usuario Inativado com sucesso!";
    }

    @Override
    public String excluir(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioEvents events = UsuarioEvents.EXCLUIR;

        events.setUsuarioHistorico(UsuarioHistorico.builder()
                .descricao("Excusão de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        stateMachineEventService.sendEvent(usuario, events, messageHeaderAccessor);
        return "Usuario Excluído com sucesso!";
    }
}
