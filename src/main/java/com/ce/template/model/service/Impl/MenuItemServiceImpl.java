package com.ce.template.model.service.Impl;

import com.ce.template.model.dto.MenuItemDTO;
import com.ce.template.model.entity.MenuItem;
import com.ce.template.model.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public MenuItemDTO findById(Integer id) {
        try {
            String sql = """
                    select
                        *
                    from template_java.menu_item
                    where e.id =?
                """;

            List<MenuItem> lista = jdbcTemplate.query(sql, (rs, rowNum) -> {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("id"));
                menuItem.setLabel(rs.getString("label"));
                menuItem.setRoute(rs.getString("route"));
                menuItem.setIcon(rs.getString("icon"));
                menuItem.setOrdenacao(rs.getInt("ordenacao"));
                menuItem.setExpanded(rs.getBoolean("expanded"));
                return menuItem;
            });

            return lista.stream().findFirst()
                    .map(menuItem -> new MenuItemDTO(
                            menuItem.getId(),
                            menuItem.getLabel(),
                            menuItem.getRoute(),
                            menuItem.getIcon(),
                            menuItem.getExpanded(),
                            menuItem.getChildren()
                    ))
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum registro encontrado.")
                    );

        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno.");
        }
    }

    @Override
    public List<MenuItemDTO> findAll() {
        try {
            String sql = """
                    select
                        *
                    from template_java.menu_item
                    WHERE pai IS NULL
                """;

            List<MenuItem> lista = jdbcTemplate.query(sql, (rs, rowNum) -> {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("id"));
                menuItem.setLabel(rs.getString("label"));
                menuItem.setRoute(rs.getString("route"));
                menuItem.setIcon(rs.getString("icon"));
                menuItem.setOrdenacao(rs.getInt("ordenacao"));
                menuItem.setExpanded(rs.getBoolean("expanded"));
                return menuItem;
            });

            for (MenuItem pai : lista) {
                carregarFilhos(pai);
            }

            return lista
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


        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno.");
        }
    }

    private void carregarFilhos(MenuItem pai) {
        String sqlFilhos = """
        SELECT *
        FROM template_java.menu_item
        WHERE pai = ?
    """;

        List<MenuItem> filhos = jdbcTemplate.query(sqlFilhos, new Object[]{pai.getId()}, (rs, rowNum) -> {
            MenuItem child = new MenuItem();
            child.setId(rs.getInt("id"));
            child.setLabel(rs.getString("label"));
            child.setRoute(rs.getString("route"));
            child.setIcon(rs.getString("icon"));
            child.setOrdenacao(rs.getInt("ordenacao"));
            child.setExpanded(rs.getBoolean("expanded"));
            return child;
        });

        if(!filhos.isEmpty())
            pai.setChildren(filhos);

        for (MenuItem filho : filhos) {
            carregarFilhos(filho);
        }
    }
}
