package jpabook.jpashop.api;

import jpabook.jpashop.api.res.OrderFlatListResponse;
import jpabook.jpashop.api.res.OrderListResponse;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.dto.OrderItemDto;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.repository.OrderQueryRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = this.orderQueryRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderListResponse> ordersV2() {
        return this.orderQueryRepository.findAll(new OrderSearch()).stream()
                .map(OrderListResponse::of).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderListResponse> ordersV3() {
        return this.orderRepository.findAllWithItem().stream()
                .map(OrderListResponse::of).collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderListResponse> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "limit", defaultValue = "100") int limit) {
        return this.orderRepository.findAllWithMemberDelivery(offset, limit).stream()
                .map(OrderListResponse::of).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderListResponse> orderV4(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                           @RequestParam(value = "limit", defaultValue = "100") int limit) {
        return this.orderQueryRepository.findOrderDtoList(offset, limit);
    }

    @GetMapping("/api/v5/orders")
    public List<OrderFlatListResponse> orderV5(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                    @RequestParam(value = "limit", defaultValue = "100") int limit) {
        return this.orderQueryRepository.findFlatOrderDtoList(offset, limit);
    }

}
