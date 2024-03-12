package africa.semicolon.shoppersDelight.services;

import africa.semicolon.shoppersDelight.dtos.request.AddProductRequest;
import africa.semicolon.shoppersDelight.dtos.response.AddProductResponse;
import africa.semicolon.shoppersDelight.models.Product;
import africa.semicolon.shoppersDelight.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppersDelightProductService implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public AddProductResponse addProduct(AddProductRequest request) {
        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(request,Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,AddProductResponse.class);
    }
}
