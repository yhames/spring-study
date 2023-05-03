package hello.mvc.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data   // 위험
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;  // Integer 클래스를 사용하는 이유는 price 값이 들어가지 않은 경우도 상정하기 때문임.
    private Integer quantity;   // 수량에 값이 들어가지 않은 경우 있을 수 있어서 클래스로 함.

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
