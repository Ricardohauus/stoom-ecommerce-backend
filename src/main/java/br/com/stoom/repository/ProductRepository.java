package br.com.stoom.repository;

import br.com.stoom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByIdAndActive(Long id, boolean active);

    Product findByNameAndActive(String name, boolean active);

    List<Product> findAllByActiveAndCategory_NameContainsAndCategory_ActiveAndBrand_NameContainsAndBrand_ActiveAndNameContains(boolean activeProduct, String categoryName, boolean activeCategory, String brandName, boolean activeBrand, String name);

    boolean existsByIdAndActive(Long id, boolean active);

    @Transactional
    @Modifying
    @Query("update Product p set p.active = :inativeOrActive where p.id = :id")
    int deleteLogicById(@Param("id") Long id, @Param("inativeOrActive") boolean inativeOrActive);
}
