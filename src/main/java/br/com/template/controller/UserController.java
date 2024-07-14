package br.com.template.controller;

import br.com.template.enums.UsuarioEvents;
import br.com.template.model.dto.*;
import br.com.template.model.entity.Usuario;
import br.com.template.model.entity.UsuarioHistorico;
import br.com.template.service.UserService;
import br.com.template.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginRequestDTO);        ;
        return new ResponseEntity<>(new LoginResponseDTO(1,token), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok().body(userService.createUser(createUserDto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UsuarioDTO dto){
        return new ResponseEntity<>(userService.atualizarUser(dto), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getEmail(@PathVariable("email") String valor){
        return new ResponseEntity<>(userService.obterPorEmail(valor), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getId(@PathVariable("id") Integer valor){
        return new ResponseEntity<>(userService.findById(valor), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> all(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativar(@RequestBody UsuarioDTO dto){
        return new ResponseEntity<>(userService.ativar(dto), HttpStatus.OK);
    }

    @PutMapping("/suspender")
    public ResponseEntity<String> suspender(@RequestBody UsuarioDTO dto){
        return new ResponseEntity<>(userService.suspender(dto), HttpStatus.OK);
    }

    @PutMapping("/inativar")
    public ResponseEntity<String> inativar(@RequestBody UsuarioDTO dto){
        return new ResponseEntity<>(userService.inativar(dto), HttpStatus.OK);
    }

    @PutMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestBody UsuarioDTO dto){
        return new ResponseEntity<>(userService.excluir(dto), HttpStatus.OK);
    }

    /*TESTES*/
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
}
