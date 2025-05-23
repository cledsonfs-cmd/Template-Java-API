package com.ce.template.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "menu_item", schema = "template_java")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String label;
    private String route;
    private String icon;
    private Boolean expanded;
    private Integer pai;
    private Integer ordenacao;

    @ManyToOne
    @JoinColumn(name = "pai", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore  // Ignorar a serialização do pai
    private MenuItem parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore  // Evita a serialização infinita de filhos
    private List<MenuItem> children;
}
