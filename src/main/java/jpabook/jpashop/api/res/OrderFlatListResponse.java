package jpabook.jpashop.api.res;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderFlatListResponse {

    private Long orderId;
    private String memberName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private Long orderItemId;
    private String itemName;
    private int orderPrice;
    private int stockQuantity;
    private int orderCount;
    private int totalPrice;

    @Builder
    public OrderFlatListResponse(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address, Long orderItemId, String itemName, int orderPrice, int stockQuantity, int orderCount, int totalPrice) {
        this.orderId = orderId;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItemId = orderItemId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.stockQuantity = stockQuantity;
        this.orderCount = orderCount;
        this.totalPrice = totalPrice;
    }

    public static OrderFlatListResponse of(Long orderId, String memberName, LocalDateTime orderDate, OrderStatus orderStatus, Address address, Long orderItemId, String itemName, int orderPrice, int stockQuantity, int orderCount, int totalPrice) {
        return OrderFlatListResponse.builder()
                .orderId(orderId)
                .memberName(memberName)
                .orderDate(orderDate)
                .orderStatus(orderStatus)
                .address(address)
                .orderItemId(orderItemId)
                .itemName(itemName)
                .orderPrice(orderPrice)
                .stockQuantity(stockQuantity)
                .orderCount(orderCount)
                .totalPrice(totalPrice)
                .build();
    }

}
