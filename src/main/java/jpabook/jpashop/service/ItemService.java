package jpabook.jpashop.service;

import jpabook.jpashop.controller.req.BookForm;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.dto.UpdateItemDto;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Transactional
    public void updateBook(Long itemId, BookForm param) {
        Item findItem = itemRepository.findOne(itemId);
        UpdateItemDto updateInfo = UpdateItemDto.builder()
                .name(param.getName())
                .price(param.getPrice())
                .stockQuantity(param.getStockQuantity())
                .build();
        findItem.updateBook(itemId, updateInfo);
    }

}
