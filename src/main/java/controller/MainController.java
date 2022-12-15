package controller;

import exception.CommonException;
import vendingmachine.Coin;
import vendingmachine.CoinGenerator;
import view.InputView;
import view.OutputView;

import java.util.EnumMap;

public class MainController {
    private static final String ZERO = "0";
    private CoinGenerator coinGenerator;

    public MainController(CoinGenerator coinGenerator) {
        this.coinGenerator = coinGenerator;
    }

    public void run() {
        try {
            int vendingMachineMoney = getVendingMachineMoney(InputView.readVendingMachineMoney());
            EnumMap<Coin, Integer> machineCoins = coinGenerator.generate(vendingMachineMoney);
            OutputView.printVendingMachineCoins(machineCoins);

        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            run();
        }
    }

    private int getVendingMachineMoney(String vendingMachineMoney) {
        validateMoney(vendingMachineMoney);
        validateMoney(vendingMachineMoney);
        return Integer.valueOf(vendingMachineMoney);
    }

    private void validateMoney(String vendingMachineMoney) {
        CommonException.validateNumericOnly(vendingMachineMoney);
        if (vendingMachineMoney.equals(ZERO)) {
            throw new IllegalArgumentException("0원은 입력할 수 없습니다.");
        }
    }
}
