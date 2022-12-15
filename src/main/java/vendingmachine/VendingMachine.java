package vendingmachine;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        payment = payment - buyingProduct.getPrice();
        productList.put(buyingProduct, productList.get(buyingProduct) - 1);
    }

    public boolean isPaymentLowerThanLowestPrice() {
        int lowestPrice = productList.keySet().stream()
                .map(product -> product.getPrice())
                .min(Comparator.comparing(x -> x))
                .orElseThrow(NoSuchElementException::new);
        return lowestPrice > payment;
    }

    public boolean isProductEmpty() {
        return productList.isEmpty();
    }

    public EnumMap<Coin, Integer> getChanges() {
        // TODO : 더 나은 로직 찾아보기
        EnumMap<Coin, Integer> result = new EnumMap<>(Coin.class);
        changes.keySet().stream()
                .filter(coin -> changes.get(coin) > 0)
                .forEach(coin -> result.put(coin, changes.get(coin)));
        return result;
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

    public void calculateChanges() {

    }
}