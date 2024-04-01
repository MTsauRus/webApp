package hgrcompany.hgrshop.Service;

import hgrcompany.hgrshop.domain.Delivery;
import hgrcompany.hgrshop.domain.Member;
import hgrcompany.hgrshop.domain.Order;
import hgrcompany.hgrshop.domain.OrderItem;
import hgrcompany.hgrshop.domain.item.Item;
import hgrcompany.hgrshop.repository.ItemRepository;
import hgrcompany.hgrshop.repository.MemberRepository;
import hgrcompany.hgrshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional // 주문은 리드온리가 아님
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // order를 persist하면 domain.Order의 cascade 옵션때문에
        // Order에 포함되어 있는 orderItems, delivery 등이 모두 자동으로 persist된다.

        return order.getId();

    }

    //취소
    @Transactional // 리드온리가 아님
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
