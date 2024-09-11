package de.oncoding.webshop.controller;

import de.oncoding.webshop.entity.ProductEntity;
import de.oncoding.webshop.exception.IdNotFoundException;
import de.oncoding.webshop.model.ProductCreateRequest;
import de.oncoding.webshop.model.ProductResponse;
import de.oncoding.webshop.model.ProductUpdateRequest;
import de.oncoding.webshop.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(
            tags = "Produkte",
            description = "Diese Schnittstelle gibt Liste von Produkten zurück.",
            responses = {
                    @ApiResponse(
                            description = "Du bist nicht authentifiziert",
                            content = @Content(),
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "War erfolgreich",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ProductResponse.class)
                                    )
                            ),
                            responseCode = "200"
                    ),

    }
    )

    @Cacheable("productsResponses")
    @GetMapping("/products")
    public List<ProductResponse> getAllProducts(
            @RequestParam(required = false) String tag
    ) {
        var products = tag == null
                ? productRepository.findAll()
                : productRepository.findByTag(tag);

        return products
                .stream()
                .map(ProductController::mapToResponse)
                .collect(Collectors.toList());
    }

    @Operation(
            tags = "Produkte",
            description = "Diese Schnittstelle gibt das Produkt der id zurück.",
            responses = {
                    @ApiResponse(
                            description = "Du bist nicht authentifiziert",
                            content = @Content(),
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "War erfolgreich",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProductResponse.class)
                            ),
                            responseCode = "200"
                    ),

            },
            parameters = {
                    @Parameter(
                            description = "Hierbei handelt es sich um die id des Produkts",
                            name = "id"
                    )

            }
    )

    @GetMapping("/products/{id}")
    public ProductResponse  getProductById(
            @PathVariable String id
    ) {
        ProductEntity product = productRepository.getReferenceById(id);

        return mapToResponse(product);

    }

    @Operation(
            tags = "Produkte"
    )
    @CacheEvict(value = "productsResponses", allEntries = true)
    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(
            @PathVariable String id
    ) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            tags = "Produkte"
    )
    @CacheEvict(value = "productsResponses", allEntries = true)
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

    @Operation(
            tags = "Produkte"
    )
    @CacheEvict(value = "productsResponses", allEntries = true)
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
