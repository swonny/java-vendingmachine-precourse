package vendingmachine;

import controller.MainController;

import java.util.Arrays;
import java.util.EnumMap;

public class Application {
    private static final int INITIALIZED_PAYMENT = 0;

    public static void main(String[] args) {
        MainController mainController = new MainController(
                new CoinGenerator(),
                new VendingMachine(initializedChanges(), INITIALIZED_PAYMENT)
        );
        mainController.run();
    }

    private static EnumMap<Coin, Integer> initializedChanges() {
        EnumMap<Coin, Integer> changes = new EnumMap<>(Coin.class);
        Arrays.stream(Coin.values())
                .forEach(coin -> changes.put(coin, 0));
        return changes;
    }
}
