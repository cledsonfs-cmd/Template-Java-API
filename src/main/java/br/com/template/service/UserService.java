package br.com.template.service;

import br.com.template.model.dto.CreateUserDto;
import br.com.template.model.dto.LoginRequestDTO;
import br.com.template.model.dto.RecoveryJwtTokenDto;
import br.com.template.model.dto.UsuarioDTO;
import br.com.template.model.entity.Usuario;

import java.util.List;

public interface UserService {

    RecoveryJwtTokenDto authenticateUser(LoginRequestDTO loginRequestDTO);

    String createUser(CreateUserDto createUserDto) ;

    String atualizarUser(UsuarioDTO usuarioDTO);

    Usuario findById(Integer id);

    List<Usuario> findAll();

    Usuario obterPorEmail(String email);

    String ativar(UsuarioDTO dto);

    String suspender(UsuarioDTO dto);

    String inativar(UsuarioDTO dto);

    String excluir(UsuarioDTO dto);
}
