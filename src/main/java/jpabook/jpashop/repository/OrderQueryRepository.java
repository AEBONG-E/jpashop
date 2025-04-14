package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.api.res.OrderListResponse;
import jpabook.jpashop.api.res.OrderFlatListResponse;
import jpabook.jpashop.api.res.SimpleOrderListResponse;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderItemDto;
import jpabook.jpashop.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jpabook.jpashop.domain.QDelivery.delivery;
import static jpabook.jpashop.domain.QItem.item;
import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;
import static jpabook.jpashop.domain.QOrderItem.orderItem;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory query;

    /**
     * 주문 검색 조회
     * @param orderSearch
     * @return List<Order>
     */
    public List<Order> findAll(OrderSearch orderSearch) {

        return query.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(statusEq(orderSearch.getOrderStatus())
                               .and(nameLike(orderSearch.getMemberName()))

                )
                .distinct()
                .limit(1000)
                .fetch();

    }

    public List<SimpleOrderListResponse> findSimpleOrderDtoList(OrderSearch orderSearch) {
        return query.select(Projections.constructor(SimpleOrderListResponse.class,
                                                    order.id.as("orderId"),
                                                    member.name,
                                                    order.orderDate,
                                                    order.status.as("orderStatus"),
                                                    delivery.address))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .where(statusEq(orderSearch.getOrderStatus())
                               .and(nameLike(orderSearch.getMemberName()))

                )
                .distinct()
                .limit(1000)
                .fetch();
    }

    public List<OrderListResponse> findOrderDtoList(int offset, int limit) {

        // 1. base orderListDto
        List<OrderListResponse> orderListDto = query
                .select(Projections.fields(OrderListResponse.class,
                                                order.id.as("orderId"),
                                                member.name,
                                                order.orderDate,
                                                order.status.as("orderStatus"),
                                                delivery.address))
                .from(order)
                .join(member).on(order.member.eq(member))
                .join(delivery).on(order.delivery.eq(delivery))
                .offset(offset)
                .limit(limit)
                .fetch();

        // 2. orderId list
        List<Long> orderIdList = orderListDto.stream()
                .map(OrderListResponse::getOrderId).toList();

        // 3. all orderItem list (solve N+1)
        List<OrderItemDto> allOrderItemList = query
                .select(Projections.fields(OrderItemDto.class,
                                                orderItem.id.as("orderItemId"),
                                                orderItem.order.id.as("orderId"),
                                                orderItem.item.name.as("itemName"),
                                                orderItem.orderPrice,
                                                orderItem.item.stockQuantity,
                                                orderItem.count.as("orderCount"),
                                                orderItem.orderPrice.multiply(orderItem.count).as("totalPrice")))
                .from(orderItem)
                .join(item).on(orderItem.item.eq(item))
                .where(orderItem.order.id.in(orderIdList))
                .fetch();

        // 4. group by orderId
        Map<Long, List<OrderItemDto>> orderItemGroupMap = allOrderItemList.stream()
                .collect(Collectors.groupingBy(OrderItemDto::getOrderId));

        // 5. mapping setting
        orderListDto.forEach(order -> order.setOrderItems(orderItemGroupMap.get(order.getOrderId())));

        return orderListDto;
    }

    public List<OrderFlatListResponse> findFlatOrderDtoList(int offset, int limit) {
        return query.select(Projections.fields(OrderFlatListResponse.class,
                                                    order.id.as("orderId"),
                                                    member.name.as("memberName"),
                                                    order.orderDate,
                                                    order.status.as("orderStatus"),
                                                    delivery.address,
                                                    orderItem.id.as("orderItemId"),
                                                    orderItem.item.name.as("itemName"),
                                                    orderItem.orderPrice,
                                                    orderItem.item.stockQuantity,
                                                    orderItem.count.as("orderCount"),
                                                    orderItem.orderPrice.multiply(orderItem.count).as("totalPrice")))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .join(order.orderItems, orderItem)
                .join(orderItem.item, item)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    private BooleanBuilder statusEq(OrderStatus status) {
        return status == null ? new BooleanBuilder() :
                new BooleanBuilder(order.status.eq(status));
    }

    private BooleanBuilder nameLike(String name) {
        return !StringUtils.hasText(name) ? new BooleanBuilder() :
                new BooleanBuilder(member.name.like(name));
    }

}
