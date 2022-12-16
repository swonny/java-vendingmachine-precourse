package vendingmachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinGeneratorTest {
    @Test
    public void test() {
        CoinGenerator coinGenerator = new CoinGenerator();
        System.out.println(coinGenerator.generate(1000));

    }

}