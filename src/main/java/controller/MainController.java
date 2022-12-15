package controller;

import exception.CommonException;
import vendingmachine.Coin;
import vendingmachine.CoinGenerator;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    private static final String ZERO = "0";
    private static final String PRODUCT_DELIMITER = ";";
    private static final String INFORMATION_DELIMITER = ",";
    private static final String PRODUCT_START_BRACKET = "[";
    private static final String PRODUCT_END_BRACKET = "]";
    private CoinGenerator coinGenerator;

    public MainController(CoinGenerator coinGenerator) {
        this.coinGenerator = coinGenerator;
    }

    public void run() {
        try {
            int vendingMachineMoney = getVendingMachineMoney(InputView.readVendingMachineMoney());
            EnumMap<Coin, Integer> machineCoins = coinGenerator.generate(vendingMachineMoney);
            OutputView.printVendingMachineCoins(machineCoins);
            List<List<String>> productInformation = getProductInformation(InputView.readProductInformation());
            // TODO : 정보 가지고 Product 객체 생성하기구현 필요
            int payment = getPayment(InputView.readPayment());

        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            run();
        }
    }

    private int getPayment(String payment) {
        validateMoney(payment);
        return Integer.valueOf(payment);
    }

    private List<List<String>> getProductInformation(String productInformation) {
        List<String> splitProduct = getSplitProduct(productInformation);
        return getSplitProductInformation(splitProduct);
    }

    private List<List<String>> getSplitProductInformation(List<String> splitProduct) {
        // TODO : [] 정규표현식으로 대체해보기
        // TODO : validate 추가하기 : , 없는 경우, 세 개 아닌 경우
        return splitProduct.stream()
                .map(product -> product.replace(PRODUCT_START_BRACKET, ""))
                .map(product -> product.replace(PRODUCT_END_BRACKET, ""))
                .map(product -> Arrays.stream(product.split(INFORMATION_DELIMITER)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<String> getSplitProduct(String productInformation) {
        // TODO : validate 추가하기 : ; 없는 경우 (그러나 한 개인 경우도 고려하기)
        return Arrays.stream(productInformation.split(PRODUCT_DELIMITER))
                .collect(Collectors.toList());
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
