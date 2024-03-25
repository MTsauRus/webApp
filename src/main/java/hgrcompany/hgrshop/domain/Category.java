package hgrcompany.hgrshop.domain;

import hgrcompany.hgrshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")) // 다대다 매핑은 중간 테이블이 필요하다.
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // 내 부모니까 당연히 다대일. 부모는 하나
    @JoinColumn(name = "parent_id") // 연관관계의 주인
    private Category parent;

    @OneToMany(mappedBy = "parent") // 부모 입장에서 자식은 여러개
    private List<Category> child = new ArrayList<>();

    //==연관관계 편의 메소드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);

    }
}
