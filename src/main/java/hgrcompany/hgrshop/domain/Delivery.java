package hgrcompany.hgrshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 연관관계의 주인이 아님.
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // enum 타입은 반드시 STRING으로 사용해야 한다. 그렇지 않으면 순서(1, 2)가 값으로 사용됨.
    private DeliveryStatus status; // READY, COMP
}
