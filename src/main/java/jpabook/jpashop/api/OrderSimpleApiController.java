package jpabook.jpashop.api;

import jpabook.jpashop.api.res.SimpleOrderListResponse;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.repository.OrderQueryRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne) Optimizing
 * Order -> Member
 * Order -> Delivery
 */
@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        return this.orderQueryRepository.findAll(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderListResponse> ordersV2() {
        return this.orderQueryRepository.findAll(new OrderSearch())
                .stream().map(SimpleOrderListResponse::of).toList();
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderListResponse> orderV3(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                 @RequestParam(value = "limit", defaultValue = "100") int limit) {
        return this.orderRepository.findAllWithMemberDelivery(offset, limit)
                .stream().map(SimpleOrderListResponse::of).toList();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderListResponse> orderV4() {
        return this.orderRepository.findOrderDtoList();
    }

    @GetMapping("/api/v5/simple-orders")
    public List<SimpleOrderListResponse> orderV5() {
        return this.orderQueryRepository.findSimpleOrderDtoList(new OrderSearch());
    }

}
