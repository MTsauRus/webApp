package hgrcompany.hgrshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") // 외래 키 이름이 member_id // 연관관계의 주인
    private Member member;

    @OneToMany(mappedBy="order", cascade = CascadeType.ALL) // orderItems 하위 객체들도 자동으로 persist/delete
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Order를 저장하면 delivery도 자동으로 저장됨.
    @JoinColumn(name = "delivery_id") // 연관관계의 주인 - 외래키가 있는 곳
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // enum 타입은 반드시 스트링
    private OrderStatus status; // 주문상태(order, cancel)

    //==연관관계 메서드==//
    /* 원래라면 이렇게 해야됨
    Member member = new Member();
    Order order = new Order();
    member.getOrders().add(order); // Orders 리스트에 order 추가
    order.setMember(member); // order에 멤버 추가
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    } // 이 메서드를 사용함으로써 member.getOrders().add(order)를 타이핑하지 않아도 됨.

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
