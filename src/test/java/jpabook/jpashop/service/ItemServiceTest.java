package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    @Rollback(value = false)
    void 상품_저장_및_찾기() {
        //given
        Item item = new Book();
        item.addStock(3);

        //when
        itemService.saveItem(item);
        Item findItem = itemService.findOne(item.getId());
        //then
        assertThat(findItem.getStockQuantity()).isEqualTo(3);
    }

}