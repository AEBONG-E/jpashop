package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired private ItemService itemService;
    @Autowired private ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    void 책상품_등록() {
        // given
        Book book = Book.builder()
                .name("JPA")
                .price(30000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();

        // when
        itemService.saveItem(book);
        em.flush();

        // then
        assertThat(itemRepository.findOne(book.getId())).isEqualTo(book);


    }

    @Test
    void 상품_리스트_조회() {
        // given
        Book book = Book.builder()
                .name("JPA")
                .price(30000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();

        itemService.saveItem(book);
        em.flush();
        em.clear();

        // when
        List<Item> findItems = itemService.findItems();


        // then
        assertThat(findItems.size()).isEqualTo(1);
    }

    @Rollback(false)
    @Test
    void 상품_단건_조회() {
        // given
        Book book = Book.builder()
                .name("JPA")
                .price(30000)
                .stockQuantity(10)
                .author("jim")
                .isbn("10001")
                .build();

        itemService.saveItem(book);
        em.flush();
        em.clear();

        // when
        Item findItem = itemService.findByName("JPA");

        // then
        assertThat(findItem.getName()).isEqualTo(book.getName());
    }

}