package hgrcompany.hgrshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id") // 기본키 매핑
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // Order 테이블에 있는 member 필드에 의해 매핑됨. 연관관계의 주인이 아님.
    // mappedBy가 붙으면 읽기 전용이 된다. Order 테이블 수정에 의해 단순히 바뀐 값만 읽을 수 있음.
    private List<Order> orders = new ArrayList<>();


}
