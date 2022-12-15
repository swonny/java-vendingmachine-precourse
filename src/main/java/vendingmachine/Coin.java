package vendingmachine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public static Coin getCoinByAmount(int coinAmount) {
        return Arrays.stream(Coin.values())
                .filter(coin -> coin.amount == coinAmount)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 금액의 동전이 없습니다."));
    }

    public int getAmount() {
        return this.amount;
    }

    public static List<Integer> getAmountOfCoins() {
        return Arrays.stream(Coin.values())
                .map(coin -> coin.amount)
                .collect(Collectors.toList());
    }
}
