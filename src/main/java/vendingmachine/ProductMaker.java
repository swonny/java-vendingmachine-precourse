package vendingmachine;

import repository.ProductRepository;

public class ProductMaker {
    public Product generate(String productName, int productPrice) {
        Product product = new Product(productName, Integer.valueOf(productPrice));
        ProductRepository.addProduct(product);
        return product;
    }
}
