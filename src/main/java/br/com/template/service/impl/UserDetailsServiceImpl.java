package br.com.template.service.impl;

import br.com.template.model.UserDetailsImpl;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuarios = usuarioRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return new UserDetailsImpl(usuarios);
    }

}
