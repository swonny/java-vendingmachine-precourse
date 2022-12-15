package exception;

public class CommonException {
    public static void validateNumericOnly(String number) {
        if (!number.matches("[0-9]+")) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
        }
    }
}
