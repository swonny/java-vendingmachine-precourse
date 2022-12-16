package controller;

import exception.CommonException;
import repository.ProductRepository;
import vendingmachine.*;
import view.InputView;
import view.OutputView;

import java.util.*;
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
        EnumMap<Coin, Integer> machineCoins = getMachineCoins(InputView.readVendingMachineMoney());
        vendingMachine.putChanges(machineCoins);
        HashMap<Product, Integer> products = getProducts(InputView.readProductInformation());
        vendingMachine.initializeProducts(products);
        int payment = getUserPayment(InputView.readPayment());
        vendingMachine.addPayment(payment);
        buyProduct();
    }

    private EnumMap<Coin, Integer> getMachineCoins(String input) {
        try {
            int vendingMachineMoney = getVendingMachineMoney(input);
            EnumMap<Coin, Integer> machineCoins = coinGenerator.generate(vendingMachineMoney);
            OutputView.printVendingMachineCoins(machineCoins);
            return machineCoins;
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            return getMachineCoins(InputView.readVendingMachineMoney());
        }
    }

    private HashMap<Product, Integer> getProducts(String productInformationInput) {
        try {
            List<String> productInformation = getSplitProducts(productInformationInput);
            List<List<String>> splitProduct = getSplitProductInformation(productInformation);
            HashMap<Product, Integer> products = makeProducts(new ProductMaker(), splitProduct);
            return products;
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            return getProducts(InputView.readProductInformation());
        }
    }

    private int getUserPayment(String payment) {
        try {
            validateMoney(payment);
            return Integer.valueOf(payment);
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            return getUserPayment(InputView.readPayment());
        }
    }

    private void buyProduct() {
        if (vendingMachine.isPaymentLowerThanLowestPrice() || vendingMachine.isProductEmpty()) {
            printResult(vendingMachine.getChanges());
            return;
        }
        OutputView.printAvailablePayment(vendingMachine.getPayment());
        buy(InputView.readProductName());

        buyProduct();
    }

    private void buy(String buyingProductName) {
        try {
            Product buyingProduct = ProductRepository.getProduct(buyingProductName);
            vendingMachine.buy(buyingProduct);
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            buy(InputView.readProductName());
        }
    }

    private List<String> getSplitProducts(String productInformation) {
        return Arrays.stream(productInformation.split(PRODUCT_DELIMITER))
                .collect(Collectors.toList());
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

    private HashMap<Product, Integer> makeProducts(ProductMaker productMaker, List<List<String>> productInformation) {
        HashMap<Product, Integer> products = new HashMap<>();
        for (List<String> product : productInformation) {
            String productName = getProductName(product);
            int productPrice = getProductPrice(product);
            int productCount = getProductCount(product);
            Product newProduct = productMaker.generate(productName, productPrice);
            products.put(newProduct, productCount);
        }
        return products;
    }

    private int getVendingMachineMoney(String vendingMachineMoney) {
        try {
            validateMoney(vendingMachineMoney);
            validateMoney(vendingMachineMoney);
            return Integer.valueOf(vendingMachineMoney);
        } catch (IllegalArgumentException exception) {
            OutputView.printExceptionMessage(exception);
            return getVendingMachineMoney(InputView.readVendingMachineMoney());
        }
    }

    private void printResult(EnumMap<Coin, Integer> changes) {
        OutputView.printAvailablePayment(vendingMachine.getPayment());
        OutputView.printChanges(changes);
    }

    private int getProductCount(List<String> product) {
        String count = product.get(PRODUCT_COUNT_INDEX);
        CommonException.validateNumericOnly(count);
        return Integer.valueOf(count);
    }

    private int getProductPrice(List<String> product) {
        String price = product.get(PRODUCT_PRICE_INDEX);
        CommonException.validateNumericOnly(price);
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

    private void validateMoney(String vendingMachineMoney) {
        CommonException.validateNumericOnly(vendingMachineMoney);
        if (vendingMachineMoney.equals(ZERO)) {
            throw new IllegalArgumentException("0원은 입력할 수 없습니다.");
        }
    }
}
