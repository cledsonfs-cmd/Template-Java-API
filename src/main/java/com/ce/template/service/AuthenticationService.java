package com.ce.template.service;

import com.ce.template.model.dto.*;

import java.util.List;

public interface AuthenticationService {
    LoginResponseDTO login(AuthenticationDTO dto);
    String register(RegisterDTO dto);
    String update(UserDTO dto);
    String updatePassword(PasswordDTO dto);
    UserDTO findById(Integer id);
    List<UserDTO> findAll();
    String ativar(Integer id);
    String inativar(Integer id);
    String excluir(Integer id);
}
