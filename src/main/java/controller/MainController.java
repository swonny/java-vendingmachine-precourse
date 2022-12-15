package controller;

import exception.CommonException;
import repository.ProductRepository;
import vendingmachine.*;
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
    private static final int PRODUCT_NAME_INDEX = 0;
    private static final int PRODUCT_PRICE_INDEX = 1;
    private static final int PRODUCT_COUNT_INDEX = 2;
    private CoinGenerator coinGenerator;
    private VendingMachine vendingMachine;

    public MainController(CoinGenerator coinGenerator, VendingMachine vendingMachine) {
        this.coinGenerator = coinGenerator;
        this.vendingMachine = vendingMachine;
    }

    public void run() {
        // TODO : 각자 따로 try-catch 구현해야할듯
        try {
            int vendingMachineMoney = getVendingMachineMoney(InputView.readVendingMachineMoney());
            EnumMap<Coin, Integer> machineCoins = coinGenerator.generate(vendingMachineMoney);
            OutputView.printVendingMachineCoins(machineCoins);
            List<List<String>> productInformation = getProductInformation(InputView.readProductInformation());
            makeProducts(new ProductMaker(), productInformation);
            int payment = getPayment(InputView.readPayment());
            vendingMachine.addPayment(payment);
            buyProduct();
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
        }
    }

    private void makeProducts(ProductMaker productMaker, List<List<String>> productInformation) {
        for (List<String> product : productInformation) {
            String productName = getProductName(product);
            int productPrice = getProductPrice(product);
            int productCount = getProductCount(product);
            Product newProduct = productMaker.generate(productName, productPrice);
            vendingMachine.addProduct(newProduct, productCount);
        }
    }

    private void buyProduct() {
        if (vendingMachine.isPaymentLowerThanLowestPrice() || vendingMachine.isProductEmpty()) {
            printResult(vendingMachine.getChanges());
            return;
        }
        OutputView.printAvailablePayment(vendingMachine.getPayment());
        Product buyingProduct = ProductRepository.getProduct(InputView.readProductName());
        vendingMachine.buy(buyingProduct);
        buyProduct();
    }

    private void printResult(EnumMap<Coin, Integer> changes) {
        OutputView.printAvailablePayment(vendingMachine.getPayment());
        System.out.println(changes);
        OutputView.printChanges(changes);
    }

    private int getProductCount(List<String> product) {
        // TODO : 숫자 아니면 예외처리
        return Integer.valueOf(product.get(PRODUCT_COUNT_INDEX));
    }

    private int getProductPrice(List<String> product) {
        // TODO : 숫자 아니면 예외처리
        String price = product.get(PRODUCT_PRICE_INDEX);
        return Integer.valueOf(price);
    }

    private String getProductName(List<String> product) {
        String name = product.get(PRODUCT_NAME_INDEX);
        validateProduct(name);
        return name;
    }

    private void validateProduct(String name) {
        if (ProductRepository.has(name)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
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
