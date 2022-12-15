package vendingmachine;

import org.junit.jupiter.api.Test;
import repository.ProductRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMakerTest {
    @Test
    public void testProductMaker_생성한프로덕트확인() {
        ProductMaker productMaker = new ProductMaker();
        assertThat(productMaker.generate("사이다", 300).getPrice())
                .isEqualTo(300);
        assertThat(productMaker.generate("사이다", 300).getName())
                .isEqualTo("사이다");
    }

    @Test
    public void testProductMaker_레포지토리확인() {
        ProductMaker productMaker = new ProductMaker();
        productMaker.generate("사이다", 300);
        assertThat(ProductRepository.has("사이다"))
                .isTrue();
    }
}