package hgrcompany.hgrshop.Service;

import hgrcompany.hgrshop.domain.Address;
import hgrcompany.hgrshop.domain.Member;
import hgrcompany.hgrshop.domain.Order;
import hgrcompany.hgrshop.domain.OrderStatus;
import hgrcompany.hgrshop.domain.item.Book;
import hgrcompany.hgrshop.domain.item.Item;
import hgrcompany.hgrshop.exception.NotEnoughStockException;
import hgrcompany.hgrshop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em; // persist를 위해 em DI
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("HGR", "춘천시", "강원대학교길", "12345");

        Book book = createBook("황경락학개론", 1000000, 10);

        int orderCount = 2;
        //when 주문을 했을 때
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then 주문 조회가 되어야 함
        Order getOrder = orderRepository.findOne(orderId);
        //assertEquals(메시지, 기댓값, 실제값)
        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 1000000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());


    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("HGR", "춘천시", "강원대학교길", "12345");
        Item item = createBook("황경락학개론", 1000000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소 시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소 상품은 재고가 증가해야 한다.", 10, item.getStockQuantity());

    }
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember("HGR", "춘천시", "강원대학교길", "12345");
        Item item = createBook("황경락학개론", 1000000, 10);

        int orderCount = 11; // 재고보다 많이 주문
        //when
        orderService.order(member.getId(), item.getId(), orderCount);
        // 익셉션이 터져야 함.

        //then
        fail("재고 수량 부족 예외가 발생해야 한다");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city, street, zipcode));
        em.persist(member);
        return member;
    }
}