package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(value = false)
@SpringBootTest
class OrderQueryRepositoryTest {

    @Autowired EntityManager em;
    @Autowired OrderQueryRepository repository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    void 주문_회원이름_검색_조회_테스트() throws Exception {
        // given
        OrderSearch searchInfo = OrderSearch.builder()
                .memberName("회원2")
                .build();
        int orderCount = 2;

        Member member1 = Member.builder()
                .name("회원1")
                .address(
                        Address.builder()
                                .city("city1")
                                .street("street1")
                                .zipcode(String.valueOf(10001))
                                .build()
                )
                .build();
        Member member2 = Member.builder()
                .name("회원2")
                .address(
                        Address.builder()
                                .city("city2")
                                .street("street2")
                                .zipcode(String.valueOf(10002))
                                .build()
                )
                .build();

        Book book1 = Book.builder()
                .name("JPA1")
                .price(20000)
                .stockQuantity(10)
                .author("author1")
                .isbn(String.valueOf(10001))
                .build();

        Book book2 = Book.builder()
                .name("JPA2")
                .price(20000)
                .stockQuantity(10)
                .author("author2")
                .isbn(String.valueOf(10002))
                .build();

        this.memberRepository.save(member1);
        this.memberRepository.save(member2);

        this.itemRepository.save(book1);
        this.itemRepository.save(book2);

        em.flush();
        em.clear();

        Member findMember1 = this.memberRepository.findOne(member1.getId());
        Member findMember2 = this.memberRepository.findOne(member2.getId());

        Item findItem1 = this.itemRepository.findOne(book1.getId());
        Item findItem2 = this.itemRepository.findOne(book2.getId());


        Long orderId1 = this.orderService.order(findMember1.getId(), findItem1.getId(), orderCount);
        Long orderId2 = this.orderService.order(findMember2.getId(), findItem2.getId(), orderCount);

        // when
        List<Order> resultList = this.repository.findAll(searchInfo);

        // then
        assertThat(resultList.size()).isEqualTo(1);

    }

    @Test
    void 주문_주문상태_검색_조회_테스트() throws Exception {
        OrderSearch searchInfo = OrderSearch.builder()
                .orderStatus(OrderStatus.ORDER)
                .build();
        int orderCount = 2;

        Member member1 = Member.builder()
                .name("회원1")
                .address(
                        Address.builder()
                                .city("city1")
                                .street("street1")
                                .zipcode(String.valueOf(10001))
                                .build()
                )
                .build();
        Member member2 = Member.builder()
                .name("회원2")
                .address(
                        Address.builder()
                                .city("city2")
                                .street("street2")
                                .zipcode(String.valueOf(10002))
                                .build()
                )
                .build();

        Book book1 = Book.builder()
                .name("JPA1")
                .price(20000)
                .stockQuantity(10)
                .author("author1")
                .isbn(String.valueOf(10001))
                .build();

        Book book2 = Book.builder()
                .name("JPA2")
                .price(20000)
                .stockQuantity(10)
                .author("author2")
                .isbn(String.valueOf(10002))
                .build();

        this.memberRepository.save(member1);
        this.memberRepository.save(member2);

        this.itemRepository.save(book1);
        this.itemRepository.save(book2);

        em.flush();
        em.clear();

        Member findMember1 = this.memberRepository.findOne(member1.getId());
        Member findMember2 = this.memberRepository.findOne(member2.getId());

        Item findItem1 = this.itemRepository.findOne(book1.getId());
        Item findItem2 = this.itemRepository.findOne(book2.getId());


        Long orderId1 = this.orderService.order(findMember1.getId(), findItem1.getId(), orderCount);
        Long orderId2 = this.orderService.order(findMember2.getId(), findItem2.getId(), orderCount);

        // when
        List<Order> resultList = this.repository.findAll(searchInfo);

        // then
        assertThat(resultList.size()).isEqualTo(2);

    }

}