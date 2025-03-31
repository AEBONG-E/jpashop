package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
@Entity
public class Category {

    @Column(name = "category_id")
    @Id @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items = new ArrayList<>();

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(Long id, String name, List<Item> items, Category parent, List<Category> children) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.parent = parent;
        this.children = children;
    }

    public void setParentInfo(Category parent) {
        this.parent = parent;
    }

    // 연관관계 메소드
    public void addChildrenCategory(Category child) {
        this.children.add(child);
        child.setParentInfo(this);
    }

}
