package com.ce.template.repository;

import com.ce.template.model.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    @Query("SELECT m FROM MenuItem m WHERE m.pai IS NULL")
    List<MenuItem> getRaizes();

}
