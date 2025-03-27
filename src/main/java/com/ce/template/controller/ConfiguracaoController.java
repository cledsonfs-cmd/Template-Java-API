package com.ce.template.controller;

import com.ce.template.model.dto.MenuItemDTO;
import com.ce.template.model.dto.UserDTO;
import com.ce.template.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("config")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ConfiguracaoController {

    private final MenuItemService menuItemService;

    @GetMapping("/menu_item/{id}")
    public ResponseEntity<MenuItemDTO> findById(@PathVariable("id") @Valid Integer id){
        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @GetMapping("/menu_item")
    public ResponseEntity<List<MenuItemDTO>> findAll(){
        return ResponseEntity.ok(menuItemService.findAll());
    }
}
