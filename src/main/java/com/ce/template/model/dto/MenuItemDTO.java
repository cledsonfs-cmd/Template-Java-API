package com.ce.template.model.dto;

import com.ce.template.model.entity.MenuItem;

import java.util.List;

public record MenuItemDTO(
        Integer id,
        String label,
        String route,
        String icon,
        Boolean expanded,
        List<MenuItem> children
) {
}
