package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum OrderStatus {
    ORDER, CANCEL
}
