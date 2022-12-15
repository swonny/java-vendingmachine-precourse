package view;

import vendingmachine.Coin;

import java.util.EnumMap;

import static constant.ViewMessage.*;

public class OutputView {
    private static void println(String value) {
        System.out.println(value);
    }

    private static void printNewLine() {
        System.out.println();
    }

    private static String integerToString(int numericValue) {
        return Integer.toString(numericValue);
    }

    private static void print(String value) {
        System.out.print(value);
    }

    private static void printFormat(String value, String formatValue) {
        System.out.printf(value, formatValue);
    }

    public static void printExceptionMessage(Exception exception) {
        println("[ERROR] " + exception.getMessage());
    }

    public static void printInfo(String value) {
        println("[INFO] " + value);
    }

    public static void printReadingMachineMoney() {
        println(READ_VENDING_MACHINE_MONEY);
    }

    public static void printVendingMachineCoins(EnumMap<Coin, Integer> machineCoins) {
        printNewLine();
        println(VENDING_MACHINE_COIN_LIST_TITLE);
        machineCoins.keySet().stream()
                .forEach(coin -> printFormat(COIN_LIST_FORMAT, coin.getAmount(), machineCoins.get(coin)));
    }

    private static void printFormat(String coinListFormat, int amount, Integer coinCount) {
        System.out.printf(coinListFormat, amount, coinCount);
        printNewLine();
    }
}
