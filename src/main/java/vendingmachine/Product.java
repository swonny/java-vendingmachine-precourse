package vendingmachine;

import java.util.List;

public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        validate(price);
        this.name = name;
        this.price = price;
    }

    private void validate(int price) {
        if (price < 100 || price % 10 != 0) {
            throw new IllegalArgumentException("잘못된 상품가격입니다.");
        }
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
