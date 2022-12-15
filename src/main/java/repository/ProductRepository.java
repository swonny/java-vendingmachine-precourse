package repository;

import vendingmachine.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static List<Product> products = new ArrayList<>();

    public static void addProduct(Product product) {
        // TODO : validate 추가
        products.add(product);
    }

    public static boolean has(String productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    public static Product getProduct(String productName) {
        return products.stream().filter(product -> product.getName().equals(productName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("찾는 상품이 없습니다."));
    }
}
