package de.oncoding.webshop.controller;

import de.oncoding.webshop.entity.ProductEntity;
import de.oncoding.webshop.exceptions.IdNotFoundException;
import de.oncoding.webshop.model.ProductCreateRequest;
import de.oncoding.webshop.model.ProductResponse;
import de.oncoding.webshop.model.ProductUpdateRequest;
import de.oncoding.webshop.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(
            @RequestParam(required = false) String tag
    ) {
        // return productRepository.findAll(tag);
        return productRepository
                .findAll()
                .stream()
                .filter((productEntity) -> tag == null || productEntity.getTags().contains(tag))
                .map(ProductController::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductResponse  getProductById(
            @PathVariable String id
    ) {
        ProductEntity product = productRepository.getReferenceById(id);

        return mapToResponse(product);

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(
            @PathVariable String id
    ) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products")
    public ProductResponse createProduct(
            @RequestBody ProductCreateRequest request
    ) {
        ProductEntity productEntity = new ProductEntity(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags()
        );
        ProductEntity savedProduct = productRepository.save(productEntity);
        return mapToResponse(savedProduct);
    }

    @PutMapping("/products/{id}")
    public ProductResponse updateProduct(
            @RequestBody ProductUpdateRequest request,
            @PathVariable String id
    ) {
        final ProductEntity product = productRepository.findById(id)
                .orElseThrow(() ->
                        new IdNotFoundException(
                        "Product with id " + id + "not found",
                        HttpStatus.BAD_REQUEST)
                );
        final ProductEntity updatedProduct = new ProductEntity(
                product.getId(),
                (request.getName() == null) ? product.getName() : request.getName(),
                (request.getDescription() == null) ? product.getDescription() : request.getDescription(),
                (request.getPriceInCent() == null) ? product.getPriceInCent() : request.getPriceInCent(),
                product.getTags()
        );

        ProductEntity savedProduct = productRepository.save(updatedProduct);
        return mapToResponse(savedProduct);
    }

    private static @NotNull ProductResponse mapToResponse(ProductEntity product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPriceInCent(),
                product.getTags()
        );
    }


}
