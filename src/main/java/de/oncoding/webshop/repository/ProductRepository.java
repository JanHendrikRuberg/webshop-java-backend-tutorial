package de.oncoding.webshop.repository;

import de.oncoding.webshop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    @Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.tags t WHERE t = :tag")
    List<ProductEntity> findByTag(String tag);
}
