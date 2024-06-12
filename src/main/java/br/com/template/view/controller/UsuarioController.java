package br.com.template.view.controller;

import java.util.List;
import java.util.Optional;

import br.com.template.model.Usuario;
import br.com.template.service.UsuarioService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.template.view.model.LoginRequest;
import br.com.template.view.model.LoginResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService servicoUsuario;

   // @ApiOperation(value = "Retorna uma lista de usuários cadastrados")
    @GetMapping
    public List<Usuario> obterTodos(){
        return servicoUsuario.obterTodos();
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> obter(@PathVariable("id") long id){
        return servicoUsuario.obterPorId(id);
    }

    @PostMapping
    public Usuario adicionar (@RequestBody Usuario usuario){
        return servicoUsuario.adicionar(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return servicoUsuario.logar(request.getEmail(), request.getSenha());
    }
}
