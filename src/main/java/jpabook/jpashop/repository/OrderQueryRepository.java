package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import java.util.List;

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
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus())
                               .and(nameLike(orderSearch.getMemberName()))

                )
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
