package com.example.core.order;

public class Order {
    private Long memberId;
    private String itemName;
    private int itemPrice;
    private int discountPrice;

    public int calcPrice() {
        return itemPrice - discountPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "memberId=" + memberId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    static public class builder {
        private Long memberId;
        private String itemName;
        private int itemPrice;
        private int discountPrice;

        public builder() {
        }

        public builder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public builder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public builder itemPrice(int itemPrice) {
            this.itemPrice = itemPrice;
            return this;
        }

        public builder discountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
            return this;
        }

        public Order build() {
            return new Order(memberId, itemName, itemPrice, discountPrice);
        }
    }
}
