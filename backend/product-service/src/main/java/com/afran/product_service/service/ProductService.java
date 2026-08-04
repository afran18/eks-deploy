package com.afran.product_service.service;

import com.afran.product_service.dto.request.CreateProductRequest;
import com.afran.product_service.dto.request.UpdateProductRequest;
import com.afran.product_service.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(UUID productId);

    ProductResponse updateProduct(UUID productId, UpdateProductRequest productRequest);

    void deleteProduct(UUID productId);
}
