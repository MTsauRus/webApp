package hgrcompany.hgrshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter // 값타입은 변경되어선 안 된다. setter가 없음.
public class Address {

    protected Address() {

    } // 값 타입은 기본 생성자가 있어야 함. public보다는 protected가 그나마 안전.

    private String city;
    private String street;
    private String zipcode;


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
