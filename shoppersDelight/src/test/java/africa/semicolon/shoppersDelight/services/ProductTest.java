package africa.semicolon.shoppersDelight.services;

import africa.semicolon.shoppersDelight.dtos.request.AddProductRequest;
import africa.semicolon.shoppersDelight.dtos.response.AddProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    public void addProductTest(){
        AddProductRequest request = new AddProductRequest();
        request.setName("Chocolate");
        request.setPrice(BigDecimal.TEN);
        request.setDescription("yummy,yum,yum!");
        request.setQuantity(10);

        AddProductResponse response = productService.addProduct(request);
        log.info("product added :: {}",response);
        assertThat(response).isNotNull();
    }



}
