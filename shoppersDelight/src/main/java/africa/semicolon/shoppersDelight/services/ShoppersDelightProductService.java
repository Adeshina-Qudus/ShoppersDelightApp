package africa.semicolon.shoppersDelight.services;

import africa.semicolon.shoppersDelight.dtos.request.AddProductRequest;
import africa.semicolon.shoppersDelight.dtos.response.AddProductResponse;
import africa.semicolon.shoppersDelight.dtos.response.ProductResponse;
import africa.semicolon.shoppersDelight.exception.ProductNotFoundException;
import africa.semicolon.shoppersDelight.models.Product;
import africa.semicolon.shoppersDelight.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.math.BigInteger.ONE;

@Service
@Slf4j
public class ShoppersDelightProductService implements ProductService{
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final ModelMapper mapper = new ModelMapper();

    @Autowired
    private ProductRepository productRepository;
    @Override
    public AddProductResponse addProduct(AddProductRequest request) {
        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(request,Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,AddProductResponse.class);
    }
    @Override
    public ProductResponse getProductBy(Long id) {
        return mapper.map(findById(id), ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getProducts(int page, int size) {
        Page<Product> productPage = productRepository.findAll(createPageRequest(page, size));
        log.info("page--->{}", productPage);
        return productPage.getContent()
                .stream()
                .map(product -> mapper.map(product,ProductResponse.class))
                .toList();
    }

    private static Pageable createPageRequest(int page, int size) {
        if (page < DEFAULT_PAGE_NUMBER) page = DEFAULT_PAGE_NUMBER;
        if (size < ONE.intValue()) size = DEFAULT_PAGE_SIZE;
        page = page - ONE.intValue();
        return PageRequest.of(page, size);
    }

    private  Product findById(Long id){
        return productRepository.findById(id).orElseThrow(
                ()->new ProductNotFoundException(
                        "Product not found"
                ));
    }
}
