package vendingmachine;

import java.util.EnumMap;
import java.util.HashMap;

public class VendingMachine {
    private HashMap<Product, Integer> productList;
    private EnumMap<Coin, Integer> changes;
    private int payment;

    public VendingMachine(EnumMap<Coin, Integer> changes, int payment) {
        this.productList = new HashMap<>();
        this.changes = changes;
        this.payment = payment;
    }

    public void buy(Product buyingProduct) {
        // TODO : 사용자 금액 --, 상품목록--
    }

    public boolean isLowerThanLowestPrice(int payment) {
        return productList.keySet().stream()
                .anyMatch(product -> product.getPrice() < payment);
    }

    public boolean isProductEmpty() {
        return productList.isEmpty();
    }

    public EnumMap<Coin, Integer> getChanges() {
        return changes;
    }

    public int getPayment() {
        return payment;
    }
}