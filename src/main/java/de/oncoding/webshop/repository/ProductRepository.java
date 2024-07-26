package de.oncoding.webshop.repository;

import de.oncoding.webshop.model.ProductCreateRequest;
import de.oncoding.webshop.model.ProductResponse;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {

    List<ProductResponse> products = new ArrayList<>();

    public ProductRepository() {
        products.add(
            new ProductResponse(
                    UUID.randomUUID().toString(),
                    "AMD Ryzen 9 5950X",
                    "grsss",
                    79900,
                    Arrays.asList("AMD", "Processor")
            ));
        products.add(
            new ProductResponse(
                    UUID.randomUUID().toString(),
                    "Intel Core i9-9900KF",
                    "grsss dfdfdfdf",
                    37900,
                    Arrays.asList("Intel", "Processor")
            ));
        products.add(
            new ProductResponse(
                    UUID.randomUUID().toString(),
                    "NVIDEA Geforce GTX 1080 Ti Black Edition 11GB",
                    "grsss",
                    74900,
                    Arrays.asList("NVIDEA", "graphics")
            ));
    }

    public List<ProductResponse> findAll(String tag) {
        if (tag == null)
            return products;
        else {
            String lowerCaseTag = tag.toLowerCase();

            return products
                    .stream()
                    .filter(p -> lowercaseTags(p).contains(lowerCaseTag))
                    .collect(Collectors.toList());
        }
    }

    private static List<String> lowercaseTags(ProductResponse p) {
        return p.getTags().stream()
                .map(tag -> tag.toLowerCase())
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> findById(String id) {
        Optional<ProductResponse> product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return product;
    }

    public void deleteById(String id) {
        this.products = products.stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());
    }

    public ProductResponse save(ProductCreateRequest request) {
        ProductResponse response = new ProductResponse(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getPriceInCent(),
                request.getTags()
        );
        products.add(response);
        return response;
    }
}
