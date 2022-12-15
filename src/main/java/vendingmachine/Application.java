package vendingmachine;

import controller.MainController;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new MainController(new CoinGenerator());
        mainController.run();
    }
}
