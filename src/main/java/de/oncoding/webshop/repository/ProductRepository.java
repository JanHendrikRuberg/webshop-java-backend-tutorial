package de.oncoding.webshop.repository;

import de.oncoding.webshop.model.ProductResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {
    public List<ProductResponse> findAll(String tag) {
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
        if (tag == null)
            return products;
        else {
            tag = tag.toLowerCase();
            List<ProductResponse> filtered = new ArrayList<>();
            for (ProductResponse p: products) {
                List<String> lowercaseTags = new ArrayList<>();
                for (String t: p.getTags()) {
                    lowercaseTags.add(t.toLowerCase());
                }

                if (lowercaseTags.contains(tag)) {
                    filtered.add(p);
                }
            }
            return filtered;
        }
    }
}
