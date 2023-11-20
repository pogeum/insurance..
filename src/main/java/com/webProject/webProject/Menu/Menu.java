package com.webProject.webProject.Menu;

import com.mysql.cj.protocol.ColumnDefinition;
import com.webProject.webProject.Store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
