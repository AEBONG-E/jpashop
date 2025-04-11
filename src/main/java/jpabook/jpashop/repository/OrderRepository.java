package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.res.SimpleOrderListResponse;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "SELECT o FROM Order o " +
                        "  JOIN FETCH o.member m " +
                        "  JOIN FETCH o.delivery d", Order.class)
                .setMaxResults(1000)
                .getResultList();
    }

    public List<SimpleOrderListResponse> findOrderDtoList() {
        return em.createQuery(
                        "SELECT new jpabook.jpashop.api.res.SimpleOrderListResponse(o.id, m.name, o.orderDate, o.status, d.address) " +
                                "FROM Order o " +
                                "  JOIN o.member m " +
                                "  JOIN o.delivery d",
                                SimpleOrderListResponse.class)
                .setMaxResults(1000)
                .getResultList();
    }

}
