package br.com.template.security;

import br.com.template.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {        
        return usuarioRepository.findByEmail(email).get();
    }

    public UserDetails obterUsuarioPorId(long id) {
        return usuarioRepository.findById(id).get();
    }    
    
}
