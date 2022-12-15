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
        validateBuyingProduct(buyingProduct);
        payment -= buyingProduct.getPrice();
        productList.put(buyingProduct, productList.get(buyingProduct) - 1);
    }

    private void validateBuyingProduct(Product buyingProduct) {
        validateProductExisting(buyingProduct);
        validateBuyablePrice(buyingProduct);
    }

    private void validateBuyablePrice(Product buyingProduct) {
        if (buyingProduct.getPrice() > payment) {
            throw new IllegalArgumentException("현재 잔액으로 구매 불가능한 상품입니다.");
        }
    }

    private void validateProductExisting(Product buyingProduct) {
        if (productList.get(buyingProduct) == 0) {
            throw new IllegalArgumentException("재고가 없습니다.");
        }
    }

    public boolean isLowerThanLowestPrice(int payment) {
        return productList.values().stream()
                .anyMatch(price -> price >= payment);
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

    public void addProduct(Product newProduct, int count) {
        productList.put(newProduct, count);
    }

    public void addPayment(int payment) {
        this.payment = payment;
    }
}