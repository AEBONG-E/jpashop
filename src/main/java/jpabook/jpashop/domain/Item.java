package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "item")
@Entity
public abstract class Item {

    @Column(name = "item_id")
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @ManyToMany
    private List<Category> categories = new ArrayList<>();

}
