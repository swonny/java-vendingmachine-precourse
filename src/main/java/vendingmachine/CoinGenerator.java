package vendingmachine;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import static vendingmachine.Coin.getAmountOfCoins;

public class CoinGenerator {
    public EnumMap<Coin, Integer> generate(int vendingMachineMoney) {
        EnumMap<Coin, Integer> changes = initializedChanges();
        List<Integer> amountOfCoins = Coin.getAmountOfCoins();
        while (vendingMachineMoney > 0) {
            int coinAmount = Randoms.pickNumberInList(amountOfCoins);
            if (vendingMachineMoney < coinAmount) {
                continue;
            }
            vendingMachineMoney -= coinAmount;
            Coin coin = Coin.getCoinByAmount(coinAmount);
            changes.put(coin, changes.get(coin) + 1);
        }
        return changes;
    }

    private static EnumMap<Coin, Integer> initializedChanges() {
        EnumMap<Coin, Integer> changes = new EnumMap<>(Coin.class);
        Arrays.stream(Coin.values())
                .forEach(coin -> changes.put(coin, 0));
        return changes;
    }
}
