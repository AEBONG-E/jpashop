package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Rollback(value = false)
    @Test
    void 상품주문() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(
                        Address.builder()
                                .city("서울")
                                .street("강남")
                                .zipcode("01234")
                                .build()
                )
                .build();
        em.persist(member);

        Book book = Book.builder()
                .name("JPA1")
                .price(20000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(20000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);

    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(
                        Address.builder()
                                .city("서울")
                                .street("강남")
                                .zipcode("01234")
                                .build()
                )
                .build();
        em.persist(member);

        Book book = Book.builder()
                .name("JPA1")
                .price(20000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();
        em.persist(book);

        int orderCount = 11;

        // when & then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount), "재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    void 주문취소() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(
                        Address.builder()
                                .city("서울")
                                .street("강남")
                                .zipcode("01234")
                                .build()
                )
                .build();
        em.persist(member);

        Book book = Book.builder()
                .name("JPA1")
                .price(20000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        assertThat(orderRepository.findOne(orderId).getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);

    }

}