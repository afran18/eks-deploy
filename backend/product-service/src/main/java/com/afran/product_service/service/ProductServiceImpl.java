package com.afran.product_service.service;

import com.afran.product_service.dto.request.CreateProductRequest;
import com.afran.product_service.dto.request.UpdateProductRequest;
import com.afran.product_service.dto.response.ProductResponse;
import com.afran.product_service.entity.Product;
import com.afran.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest productRequest) {
        if(productRepository.existsByProductName(productRequest.productName())){
            throw new RuntimeException("Product already exists");
        }

        Product product = Product.builder()
                .productName(productRequest.productName())
                .price(productRequest.price())
                .quantity(productRequest.quantity())
                .build();

        Product savedProduct = productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product does not exist"));

        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(UUID productId, UpdateProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getProductName().equals(productRequest.productName())
                && productRepository.existsByProductName(productRequest.productName())) {

            throw new RuntimeException("Product already exists");
        }

        product.setProductName(productRequest.productName());
        product.setQuantity(productRequest.quantity());
        product.setPrice(productRequest.price());

        Product updatedProduct = productRepository.save(product);

        return mapToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
