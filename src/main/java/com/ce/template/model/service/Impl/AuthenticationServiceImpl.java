package com.ce.template.model.service.Impl;

import com.ce.template.model.dto.*;
import com.ce.template.model.entity.User;
import com.ce.template.model.repository.UserRepository;
import com.ce.template.security.service.TokenService;
import com.ce.template.model.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    @Override
    public LoginResponseDTO login(AuthenticationDTO dto) {
        if (dto.login() == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo Login obrigatório.");
        }

        if (dto.password() == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo password obrigatório.");
        }

        if (dto.login().trim().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo Login obrigatório.");
        }

        if (dto.password().trim().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo password obrigatório.");
        }

        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
            var auth = this.authenticationManager.authenticate(userNamePassword);
            User user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);
            return new LoginResponseDTO(user.getId(), user.getLogin(), user.getRole(), token);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @Override
    public String register(RegisterDTO dto) {
        if (dto.login() == null || dto.login().trim().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo Login obrigatório.");
        }

        if (dto.password() == null || dto.password().trim().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo password obrigatório.");
        }

        if (dto.role() == null || dto.role().getRole().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo role obrigatório.");
        }

        if (dto.password().trim().length()!=8) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo password deve ter 8 caracteres.");
        }

        UserDetails userDetails = repository.findByLogin(dto.login());

        if(userDetails != null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já existente.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = User.builder()
                .login(dto.login())
                .password(encryptedPassword)
                .role(dto.role())
                .ativo(true)
                .build();

        repository.save(newUser);

        return "Usuário registrado com sucesso.";
    }

    @Override
    public String update(UserDTO dto) {
        if (dto.role().getRole().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo role obrigatório.");
        }

        Optional<User> usuarioOpt = repository.findById(dto.id());
        if (usuarioOpt.isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não existente.");
        }

        User userUpdate = usuarioOpt.get();

        userUpdate.setRole(dto.role());
        userUpdate.setAtivo(dto.ativo());

        repository.save(userUpdate);

        return "Usuário atualizado com sucesso.";
    }

    @Override
    public String updatePassword(PasswordDTO dto) {
        if (dto.password().trim().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campo password obrigatório.");
        }

        if (dto.password().trim().length()!=8) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo password deve ter 8 caracteres.");
        }

        Optional<User> usuarioOpt = repository.findById(dto.id());
        if (usuarioOpt.isPresent()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
            User usuario = usuarioOpt.get();
            usuario.setPassword(encryptedPassword);
            repository.save(usuario);
            return "Password alterado com sucesso.";
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado.");
        }
    }

    @Override
    public UserDTO findById(Integer id) {
        Optional<User> usuarioOpt = repository.findById(id);

        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado.");
        }

        return usuarioOpt
                .map(user -> new UserDTO(user.getId(), user.getLogin(), user.getRole(), user.getAtivo()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado."));
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream()
                .map(u -> new UserDTO(u.getId(), u.getLogin(), u.getRole(), u.getAtivo()))
                .toList();
    }

    @Override
    public String ativar(Integer id) {
        Optional<User> usuarioOpt = repository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado.");
        }
        User usuario = usuarioOpt.get();
        usuario.setAtivo(true);
        repository.save(usuario);
        return "Usuário Ativado com sucesso.";
    }

    @Override
    public String inativar(Integer id) {
        Optional<User> usuarioOpt = repository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado.");
        }
        User usuario = usuarioOpt.get();
        usuario.setAtivo(false);
        repository.save(usuario);
        return "Usuário Inativado com sucesso.";

    }

    @Override
    public String excluir(Integer id) {
        Optional<User> usuarioOpt = repository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum registro encontrado.");
        }
        repository.delete(usuarioOpt.get());
        return "Usuário excluído com sucesso.";
    }
}
