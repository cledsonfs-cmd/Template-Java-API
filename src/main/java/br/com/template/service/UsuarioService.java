package br.com.template.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import br.com.template.model.Usuario;
import br.com.template.view.model.UsuarioDTO;
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

public interface UsuarioService {

    List<Usuario> obterTodos();

    Optional<Usuario> obterPorId(long id);

    Optional<Usuario> obterPorEmail(String email);

    Usuario adicionar(Usuario usuario);

    LoginResponse logar(String email, String senha);

    String ativar(UsuarioDTO dto);

    String suspender(UsuarioDTO dto);

    String inativar(UsuarioDTO dto);

    String excluir(UsuarioDTO dto);

}
