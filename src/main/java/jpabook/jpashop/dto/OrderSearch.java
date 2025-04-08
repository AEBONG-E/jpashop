package jpabook.jpashop.dto;

import jpabook.jpashop.domain.OrderStatus;
import lombok.*;

@Data
@NoArgsConstructor
public class OrderSearch {

    private String memberName;          // 회원 이름
    private OrderStatus orderStatus;    // 주문 상태 (ORDER|CANCEL)

    @Builder
    public OrderSearch(String memberName, OrderStatus orderStatus) {
        this.memberName = memberName;
        this.orderStatus = orderStatus;
    }

}
