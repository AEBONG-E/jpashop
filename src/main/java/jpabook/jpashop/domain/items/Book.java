package jpabook.jpashop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    @Builder
    public Book(String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
    }

}
