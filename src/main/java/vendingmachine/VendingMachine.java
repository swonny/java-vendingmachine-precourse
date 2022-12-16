package vendingmachine;

import java.util.*;
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
                        .get();
        return lowestPrice > payment;
    }

    public boolean isProductEmpty() {
        return productList.isEmpty();
    }

    public EnumMap<Coin, Integer> getChanges() {
        EnumMap<Coin, Integer> result = new EnumMap<>(Coin.class);
        changes.keySet().stream()
                .filter(coin -> changes.get(coin) > 0)
                .forEach(coin -> result.put(coin, changes.get(coin)));
        return result;
    }

    public int getPayment() {
        return payment;
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
        // TODO : 수정 필요
        EnumMap<Coin, Integer> returnChanges = new EnumMap<>(Coin.class);
        List<Coin> availableCoins = getAvailableCoins();
        for (Coin coin : availableCoins) {
            if (payment == 0) {
                break;
            }
            if (payment >= coin.getAmount()) {
                payment -= coin.getAmount();
                returnChanges.putIfAbsent(coin, 0);
                returnChanges.put(coin, returnChanges.get(coin) + 1);
            }
        }
        if (payment > 0) {

        }
    }

    private List<Coin> getAvailableCoins() {
        return changes.keySet().stream()
                .filter(coin -> changes.get(coin) > 0)
                .collect(Collectors.toList());
    }

    public void putChanges(EnumMap<Coin, Integer> changes) {
        this.changes = changes;
    }

    public void initializeProducts(HashMap<Product, Integer> products) {
        productList.putAll(products);
    }
}