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
}
