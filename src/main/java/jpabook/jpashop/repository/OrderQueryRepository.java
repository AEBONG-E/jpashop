package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.api.res.SimpleOrderListResponse;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.List;

import static jpabook.jpashop.domain.QDelivery.delivery;
import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

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

    public List<SimpleOrderListResponse> findOrderDtoList(OrderSearch orderSearch) {
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

    private BooleanBuilder statusEq(OrderStatus status) {
        return status == null ? new BooleanBuilder() :
                new BooleanBuilder(order.status.eq(status));
    }

    private BooleanBuilder nameLike(String name) {
        return !StringUtils.hasText(name) ? new BooleanBuilder() :
                new BooleanBuilder(member.name.like(name));
    }

}
