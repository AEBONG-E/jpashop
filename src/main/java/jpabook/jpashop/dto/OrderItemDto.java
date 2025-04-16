package jpabook.jpashop.dto;

import jpabook.jpashop.domain.OrderItem;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int stockQuantity;
    private int orderCount;
    private int totalPrice;

    @Builder
    public OrderItemDto(Long orderItemId, Long orderId, String itemName, int orderPrice, int stockQuantity, int orderCount, int totalPrice) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.stockQuantity = stockQuantity;
        this.orderCount = orderCount;
        this.totalPrice = totalPrice;
    }

    public static OrderItemDto of(OrderItem orderItem) {
        if (orderItem == null) return null;
        return OrderItemDto.builder()
                .orderItemId(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .itemName(orderItem.getItem().getName())
                .orderPrice(orderItem.getOrderPrice())
                .stockQuantity(orderItem.getItem().getStockQuantity())
                .orderCount(orderItem.getCount())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    public static OrderItemDto of(Long orderItemId, Long orderId, String itemName, int orderPrice, int stockQuantity, int orderCount) {
        return OrderItemDto.builder()
                .orderItemId(orderItemId)
                .orderId(orderId)
                .itemName(itemName)
                .orderPrice(orderPrice)
                .stockQuantity(stockQuantity)
                .orderCount(orderCount)
                .totalPrice(orderPrice)
                .build();
    }

}
