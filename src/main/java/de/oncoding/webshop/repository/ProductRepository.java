package de.oncoding.webshop.repository;

import de.oncoding.webshop.model.ProductResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepository {
    List<ProductResponse> products = Arrays.asList(
            new ProductResponse(
                    "1",
                    "AMD Ryzen 9 5950X",
                    "grsss",
                    79900,
                    Arrays.asList("AMD", "Processor")
            ),
            new ProductResponse(
                    "2",
                    "Intel Core i9-9900KF",
                    "grsss dfdfdfdf",
                    37900,
                    Arrays.asList("Intel", "Processor")
            ),
            new ProductResponse(
                    "3",
                    "NVIDEA Geforce GTX 1080 Ti Black Edition 11GB",
                    "grsss",
                    74900,
                    Arrays.asList("NVIDEA", "graphics")
            )
    );
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
}
