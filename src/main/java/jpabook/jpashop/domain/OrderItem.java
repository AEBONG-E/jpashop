package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
@Entity
public class OrderItem {

    @Column(name = "order_item_id")
    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private int orderPrice;     // 주문가격
    private int count;          // 주문수량

    @Builder
    public OrderItem(Long id, Item item, Order order, int orderPrice, int count) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void setOrderInfo(Order order) {
        this.order = order;
    }

}
