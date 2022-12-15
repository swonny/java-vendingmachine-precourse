package vendingmachine;

import java.util.Collections;
import java.util.EnumMap;

public class CoinGenerator {
    public EnumMap<Coin, Integer> generate(int vendingMachineMoney) {
        // TODO : return값 변경
        // TODO : 코인 목록 생성과 0 초기화를 위해 init() 추가
        EnumMap<Coin, Integer> tmp = new EnumMap<>(Coin.class);
        tmp.put(Coin.COIN_10, 1);
        tmp.put(Coin.COIN_500, 1);
        return tmp;
    }
}
