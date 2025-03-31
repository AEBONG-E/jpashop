package jpabook.jpashop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;

    @Builder
    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
