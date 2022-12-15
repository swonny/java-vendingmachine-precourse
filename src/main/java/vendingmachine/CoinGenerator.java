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
            // TODO : 메소드 분리 가능해보임
            int coinAmount = Randoms.pickNumberInList(amountOfCoins);
            if (vendingMachineMoney < coinAmount) {
                continue;
            }
            vendingMachineMoney -= coinAmount;
            Coin coin = Coin.getCoinByAmount(coinAmount);
            // TODO : 해시맵 가져오지 않고 바로 value에서 작업할 수 있는 방법 찾아보기
            changes.put(coin, changes.get(coin) + 1);
        }
        return changes;
    }

    // TODO : APplication과 중복 -> 합치던가 클래스 하나 만들던가 하기
    private static EnumMap<Coin, Integer> initializedChanges() {
        EnumMap<Coin, Integer> changes = new EnumMap<>(Coin.class);
        Arrays.stream(Coin.values())
                .forEach(coin -> changes.put(coin, 0));
        return changes;
    }
}
