package vendingmachine;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    @Test
    public void testVendingMachine_payment가더작을때() {
        EnumMap<Coin, Integer> changes = new EnumMap<Coin, Integer>(Coin.class);
        VendingMachine vendingMachine = new VendingMachine(changes, 0);
        vendingMachine.addPayment(100);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(new Product("사이다", 1000), 1);
        vendingMachine.initializeProducts(products);
        assertThat(vendingMachine.isPaymentLowerThanLowestPrice())
                .isTrue();
    }

    @Test
    public void testVendingMachine_payment가더클때() {
        EnumMap<Coin, Integer> changes = new EnumMap<Coin, Integer>(Coin.class);
        VendingMachine vendingMachine = new VendingMachine(changes, 0);
        vendingMachine.addPayment(1000);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(new Product("사이다", 1000), 1);
        vendingMachine.initializeProducts(products);
        assertThat(vendingMachine.isPaymentLowerThanLowestPrice())
                .isFalse();
    }

    @Test
    public void testVendingMachine_payment가더작은데_상품이0개일때() {
        EnumMap<Coin, Integer> changes = new EnumMap<Coin, Integer>(Coin.class);
        VendingMachine vendingMachine = new VendingMachine(changes, 0);
        vendingMachine.addPayment(1200);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(new Product("사이다", 2000), 0);
        products.put(new Product("콜라", 1000), 1);
        vendingMachine.initializeProducts(products);
        assertThat(vendingMachine.isPaymentLowerThanLowestPrice())
                .isFalse();
    }
}