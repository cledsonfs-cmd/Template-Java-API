package br.com.template.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import br.com.template.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.template.exception.CampoObrigatorioException;
import br.com.template.exception.SenhaException;
import br.com.template.exception.UsuarioNotFoundException;
import br.com.template.repository.UsuarioRepository;
import br.com.template.security.JWTService;
import br.com.template.view.model.LoginResponse;

@Service
public class UsuarioService {
    
    //private static final String hederPrefix = "Bearer ";
    
    @Autowired
    private UsuarioRepository repositorioUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<Usuario> obterTodos(){
        return repositorioUsuario.findAll();
    }

    public Optional<Usuario> obterPorId(long id){
        return repositorioUsuario.findById(id);
    }

    public Optional<Usuario> obterPorEmail(String email){
        return repositorioUsuario.findByEmail(email);
    }

    public Usuario adicionar(Usuario usuario){
        usuario.setId(null);

        if(usuario.getNome().trim().isEmpty()){
            throw new CampoObrigatorioException();
        }

        if(usuario.getEmail().trim().isEmpty()){
            throw new CampoObrigatorioException();
        }

        if(usuario.getPassword().trim().isEmpty()){
            throw new CampoObrigatorioException();
        }

        if(usuario.getPassword().trim().length()<6){
            throw new SenhaException();
        }

        if(obterPorEmail(usuario.getEmail()).isPresent()){            
            throw new InputMismatchException("Já existe um usuario cadastro com o email: " + usuario.getEmail());
        }

        // Aqui eu estou codificando a senha para não ficar publica, gerando um hash
        String senha = passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senha);
        
        return repositorioUsuario.save(usuario);
    }

    public LoginResponse logar(String email, String senha){
        
        if(email.isEmpty()){
            throw new CampoObrigatorioException();
        }

        if(senha.isEmpty()){
            throw new CampoObrigatorioException();
        }
        
        Optional<Usuario> existe = obterPorEmail(email);
        if(!existe.isPresent()){
            throw new UsuarioNotFoundException();
        }

        // Aqui que a autenticação acontece magicamente.
        Authentication autenticacao = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList()));

            // Aqui eu passo a nova autenticação para o Spring Security cuidar pra mim.
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Gero o token do usuario para devolver a ele.
        // Bearer acf12ghb3jhujh.asdfresdtuopi36jklo541.ascfhjvvcv
        String token = jwtService.gerarToken(autenticacao);
        //String token = hederPrefix + jwtService.gerarToken(autenticacao);

        Usuario usuario = repositorioUsuario.findByEmail(email).get();

        return new LoginResponse(usuario.getId(), token);      
    }
}
