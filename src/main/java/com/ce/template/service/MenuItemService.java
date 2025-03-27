package com.ce.template.service;

import com.ce.template.model.dto.MenuItemDTO;

import java.util.List;

public interface MenuItemService {
    MenuItemDTO findById(Integer id);
    List<MenuItemDTO> findAll();
}
