package com.ce.template.service.Impl;

import com.ce.template.model.dto.MenuItemDTO;
import com.ce.template.model.entity.MenuItem;
import com.ce.template.repository.MenuItemRepository;
import com.ce.template.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repository;

    @Override
    public MenuItemDTO findById(Integer id) {
        MenuItem menuItem = repository.findById(id).orElseThrow(RuntimeException::new);
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getLabel(),
                menuItem.getRoute(),
                menuItem.getIcon(),
                menuItem.getExpanded(),
                menuItem.getChildren()
        );
    }

    @Override
    public List<MenuItemDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(menuItem -> new MenuItemDTO(
                        menuItem.getId(),
                        menuItem.getLabel(),
                        menuItem.getRoute(),
                        menuItem.getIcon(),
                        menuItem.getExpanded(),
                        menuItem.getChildren()
                ))
                .collect(Collectors.toList());
    }
}
