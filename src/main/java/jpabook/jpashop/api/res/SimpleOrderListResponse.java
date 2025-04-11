package jpabook.jpashop.api.res;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SimpleOrderListResponse {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    @Builder
    public SimpleOrderListResponse(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public static SimpleOrderListResponse of(Order order) {
        if (order == null)  return null;
        return SimpleOrderListResponse.builder()
                .orderId(order.getId())
                .name(order.getMember().getName())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .address(order.getDelivery().getAddress())
                .build();
    }

    public static SimpleOrderListResponse of(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        return SimpleOrderListResponse.builder()
                .orderId(orderId)
                .name(name)
                .orderDate(orderDate)
                .orderStatus(orderStatus)
                .address(address)
                .build();
    }

}
