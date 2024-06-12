package br.com.template.service.impl;

import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioNotFoundException;
import br.com.template.model.Usuario;
import br.com.template.repository.UsuarioRepository;
import br.com.template.security.JWTService;
import br.com.template.service.UsuarioService;
import br.com.template.statemachine.enums.UsuarioEvents;
import br.com.template.statemachine.service.StateMachineEventService;
import br.com.template.view.model.LoginResponse;
import br.com.template.view.model.UsuarioDTO;
import br.com.template.view.model.UsuarioHistoricoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class UsuarioServiceImpl implements UsuarioService {

    private ModelMapper modelMapper = new ModelMapper();
    private final StateMachineEventService stateMachineEventService;

    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    //@Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<Usuario> obterTodos() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Optional<Usuario> obterPorId(long id) {
        return repositorioUsuario.findById(id);
    }

    @Override
    public Optional<Usuario> obterPorEmail(String email) {
        return repositorioUsuario.findByEmail(email);
    }

    @Override
    public Usuario adicionar(Usuario usuario) {
        usuario.setId(null);

        if (usuario.getNome().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (usuario.getEmail().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (usuario.getPassword().trim().isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (usuario.getPassword().trim().length() < 6) {
            throw new SenhaException();
        }

        if (obterPorEmail(usuario.getEmail()).isPresent()) {
            throw new InputMismatchException("Já existe um usuario cadastro com o email: " + usuario.getEmail());
        }

        // Aqui eu estou codificando a senha para não ficar publica, gerando um hash
        String senha = passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senha);

        return repositorioUsuario.save(usuario);
    }

    @Override
    public LoginResponse logar(String email, String senha) {

        if (email.isEmpty()) {
            throw new CampoObrigatorioException();
        }

        if (senha.isEmpty()) {
            throw new CampoObrigatorioException();
        }

        Optional<Usuario> existe = obterPorEmail(email);
        if (!existe.isPresent()) {
            throw new UsuarioNotFoundException();
        }

        // Aqui que a autenticação acontece magicamente.
        Authentication autenticacao = new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList());

        // Aqui eu passo a nova autenticação para o Spring Security cuidar pra mim.
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Gero o token do usuario para devolver a ele.
        // Bearer acf12ghb3jhujh.asdfresdtuopi36jklo541.ascfhjvvcv
        String token = jwtService.gerarToken(autenticacao);
        //String token = hederPrefix + jwtService.gerarToken(autenticacao);

        Usuario usuario = repositorioUsuario.findByEmail(email).get();

        return new LoginResponse(usuario.getId(), token);
    }

    @Override
    public String ativar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);

        UsuarioEvents events = UsuarioEvents.ATIVAR;

        events.setUsuarioHistoricoDTO(UsuarioHistoricoDTO.builder()
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

        events.setUsuarioHistoricoDTO(UsuarioHistoricoDTO.builder()
                .descricao("Suspensão de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        messageHeaderAccessor.setHeader("dto", dto);
        return "Usuario Suspenso com sucesso!";
    }

    @Override
    public String inativar(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioEvents events = UsuarioEvents.INATIVAR;

        events.setUsuarioHistoricoDTO(UsuarioHistoricoDTO.builder()
                .descricao("Inativação de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        messageHeaderAccessor.setHeader("dto", dto);
        return "Usuario Inativado com sucesso!";
    }

    @Override
    public String excluir(UsuarioDTO dto) {
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        UsuarioEvents events = UsuarioEvents.EXCLUIR;

        events.setUsuarioHistoricoDTO(UsuarioHistoricoDTO.builder()
                .descricao("Excusão de usuário.")
                .build()
        );

        MessageHeaderAccessor messageHeaderAccessor = new MessageHeaderAccessor();
        messageHeaderAccessor.setHeader("dto", dto);
        return "Usuario Excluído com sucesso!";
    }


}
