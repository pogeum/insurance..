package com.webProject.webProject.Menu;

import com.mysql.cj.protocol.ColumnDefinition;
import com.webProject.webProject.Photo.Photo;
import com.webProject.webProject.Store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String menuName;

    private Integer price;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE)
    private List<Photo> photoList;
}
