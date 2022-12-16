package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public static String readVendingMachineMoney() {
        OutputView.printReadingMachineMoney();
        return read();
    }

    private static String read() {
        return Console.readLine();
    }

    public static String readProductInformation() {
        OutputView.printReadingProductInformation();
        return read();
    }

    public static String readPayment() {
        OutputView.printReadingPayment();
        return read();
    }

    public static String readProductName() {
        OutputView.printReadingProductName();
        return read();
    }
}
