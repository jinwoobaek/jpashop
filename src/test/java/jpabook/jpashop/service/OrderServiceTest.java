package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    void 상품주문() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddrress(new Address("서울", "관악", "123"));
        memberService.join(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        itemService.saveItem(book);

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 3);
        Order findOrder = orderRepository.findOne(orderId);

        //then
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(30000);

        assertThat(book.getStockQuantity()).isEqualTo(7); // 재소 수량
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddrress(new Address("서울", "관악", "123"));
        memberService.join(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        itemService.saveItem(book);

        int orderCount = 11;

        //when
        NotEnoughStockException e = assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount)
        );

        //then
        assertThat(e.getMessage()).isEqualTo("need more stock");
    }

    @Test
    void 주문취소() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setAddrress(new Address("서울", "관악", "123"));
        memberService.join(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        itemService.saveItem(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        //then
        Order canceledOrder = orderRepository.findOne(orderId);

        assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }
}