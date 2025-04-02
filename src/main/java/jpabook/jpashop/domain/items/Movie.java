package jpabook.jpashop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Movie extends Item {

    private String director;
    private String actor;

    @Builder
    public Movie(Long id, String name, int price, int stockQuantity, List<Category> categories, String director, String actor) {
        super(id,
              name,
              price,
              stockQuantity,
              categories);
        this.director = director;
        this.actor = actor;
    }

}
