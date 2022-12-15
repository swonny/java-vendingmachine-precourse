package view;

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

}
