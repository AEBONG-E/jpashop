package jpabook.jpashop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Album extends Item {

    private String artist;
    private String etc;

    @Builder
    public Album(String artist, String etc) {
        this.artist = artist;
        this.etc = etc;
    }

}
