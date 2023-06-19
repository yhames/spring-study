package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * JPA를 사용하기 위해서는 매핑 정보를 설정해야함.
 */
@Data
@Entity // `JPA`에서 엔티티로 인식
//@Table(name = "item")   // 클래스 이름과 동일하면 생략가능
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY == auto increment in MySql
    private Long id;

    @Column(name = "item_name", length = 10)    // camel to snake 자동변환 가능하여 생략가능, length는 DDL 생성시 컬럼의 길이로 활용
    private String itemName;

//    @Column(name = "price") // 필드 이름과 동일하면 생략가능
    private Integer price;

    private Integer quantity;

    public Item() {
        // JPA 사용시 public 혹은 protected 기본 생성자 필수
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
