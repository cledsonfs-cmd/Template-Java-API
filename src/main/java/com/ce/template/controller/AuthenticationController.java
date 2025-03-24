package com.ce.template.controller;

import com.ce.template.model.dto.*;
import com.ce.template.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto){
        return ResponseEntity.ok(service.login(dto));
    }

   @PostMapping("/register")
   public ResponseEntity<SuccessDTO> register(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(new SuccessDTO(service.register(dto)));
    }

    @PostMapping("/update")
    public ResponseEntity<SuccessDTO> update(@RequestBody UserDTO dto){
        return ResponseEntity.ok(new SuccessDTO(service.update(dto)));
    }

    @PostMapping("/update_password")
    public ResponseEntity<SuccessDTO> updatePassword(@RequestBody @Valid PasswordDTO dto){
        return ResponseEntity.ok(new SuccessDTO(service.updatePassword(dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") @Valid Integer id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/ativar/{id}")
    public ResponseEntity<SuccessDTO> ativar(@PathVariable("id") @Valid Integer id){
        return ResponseEntity.ok(new SuccessDTO(service.ativar(id)));
    }

    @GetMapping("/inativar/{id}")
    public ResponseEntity<SuccessDTO> inativar(@PathVariable("id") @Valid Integer id){
        return ResponseEntity.ok(new SuccessDTO(service.inativar(id)));
    }

    @GetMapping("/excluir/{id}")
    public ResponseEntity<SuccessDTO> excluir(@PathVariable("id") @Valid Integer id){
        return ResponseEntity.ok(new SuccessDTO(service.excluir(id)));
    }

}
