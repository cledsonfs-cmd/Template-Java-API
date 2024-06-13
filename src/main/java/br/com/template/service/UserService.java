package br.com.template.service;

import br.com.template.model.dto.CreateUserDto;
import br.com.template.model.dto.LoginRequestDTO;
import br.com.template.model.dto.RecoveryJwtTokenDto;
import br.com.template.model.dto.UsuarioDTO;
import br.com.template.model.entity.Usuario;

import java.util.Optional;

public interface UserService {

    RecoveryJwtTokenDto authenticateUser(LoginRequestDTO loginRequestDTO);

    void createUser(CreateUserDto createUserDto) ;

    Optional<Usuario> obterPorEmail(String email);

    String ativar(UsuarioDTO dto);

    String suspender(UsuarioDTO dto);

    String inativar(UsuarioDTO dto);

    String excluir(UsuarioDTO dto);
}
