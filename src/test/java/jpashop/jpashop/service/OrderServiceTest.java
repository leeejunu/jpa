package jpashop.jpashop.service;

import jakarta.persistence.EntityManager;
import jpashop.jpashop.domain.Address;
import jpashop.jpashop.domain.Member;
import jpashop.jpashop.domain.Order;
import jpashop.jpashop.domain.OrderStatus;
import jpashop.jpashop.domain.item.Book;
import jpashop.jpashop.domain.item.Item;
import jpashop.jpashop.exception.NotEnoughStockQuantity;
import jpashop.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() {
        Member member = createMember();
        Book item = createBook("시골 JPA", 15000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");

    }



    @Test
    public void 주문취소() throws Exception{
        Member member = createMember();
        Book item = createBook("시골 JPA", 15000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문의 상태는 CANCEL이다.");
        assertEquals(10, item.getStockQuantity(), "주문취소한 상품의 재고 검증");
    }

    @Test
    public void 상품주문_재고수량초과() {
        Member member = createMember();
        em.persist(member);
        Item book = createBook("시골 JPA1", 14000, 8);
        em.persist(book);

        int orderCount = 11;

        Assertions.assertThrows(NotEnoughStockQuantity.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    private Book createBook(String bookName, int price, int stockQuantity) {
        Book item = new Book();
        item.setName(bookName);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("Lee");
        member.setAddress(new Address("부산", "북구", "40002"));
        em.persist(member);
        return member;
    }
}