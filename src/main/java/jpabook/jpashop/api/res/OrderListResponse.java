package jpabook.jpashop.api.res;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderItemDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderListResponse {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    @Builder
    public OrderListResponse(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItemDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

    public static OrderListResponse of(Order order) {
        if (order == null) return null;
        return OrderListResponse.builder()
                .orderId(order.getId())
                .name(order.getMember().getName())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getStatus())
                .address(order.getDelivery().getAddress())
                .orderItems(order.getOrderItems().stream().map(OrderItemDto::of).toList())
                .build();
    }

    public static OrderListResponse of(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, List<OrderItem> orderItems) {
        return OrderListResponse.builder()
                .orderId(orderId)
                .name(name)
                .orderDate(orderDate)
                .orderStatus(orderStatus)
                .address(address)
                .orderItems(orderItems.stream().map(OrderItemDto::of).toList())
                .build();
    }

}
