package br.com.template.service;

import br.com.template.model.UserDetailsImpl;
import br.com.template.model.entity.Usuario;
import br.com.template.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsServiceImpl {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
