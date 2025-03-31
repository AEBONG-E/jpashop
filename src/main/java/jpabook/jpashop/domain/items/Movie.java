package jpabook.jpashop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Movie extends Item {

    private String director;
    private String actor;

    @Builder
    public Movie(String director, String actor) {
        this.director = director;
        this.actor = actor;
    }

}
