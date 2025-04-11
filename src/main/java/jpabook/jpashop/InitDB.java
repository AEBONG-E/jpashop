package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.items.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 총 주문 2개
 * userA
 *  JPA1 BOOK
 *  JPA2 BOOK
 * userB
 *  SPRING1 BOOK
 *  SPRING2 BOOK
 */
@RequiredArgsConstructor
@Component
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Transactional
    @RequiredArgsConstructor
    @Component
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = createMember("userA", "서울", "1", "11111");
            em.persist(member1);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 10000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem12 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member1);

            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem12);
            em.persist(order);
        }

        public void dbInit2() {
            Member member2 = createMember("userB", "대전", "2", "211111");
            em.persist(member2);

            Book book1 = createBook("SPRING1 BOOK", 15000, 100);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 2);
            OrderItem orderItem12 = OrderItem.createOrderItem(book2, 40000, 2);

            Delivery delivery = createDelivery(member2);

            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem12);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            return Member.builder()
                    .name(name)
                    .address(
                            Address.builder()
                                    .city(city)
                                    .street(street)
                                    .zipcode(zipcode)
                                    .build())
                    .build();
        }

        private Book createBook(String name, int price, int stockQuantity) {
            return Book.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .build();
        }

        private Delivery createDelivery(Member member) {
            return Delivery.builder()
                    .address(member.getAddress())
                    .build();
        }

    }

}
