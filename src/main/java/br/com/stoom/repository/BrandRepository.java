package br.com.stoom.repository;

import br.com.stoom.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByNameAndActive(String name, boolean active);

    boolean existsByIdAndActive(Long id, boolean active);

    Brand findByIdAndActive(Long id, boolean active);

    List<Brand> findAllByActive(boolean active);

    @Transactional
    @Modifying
    @Query("update Brand p set p.active = :inativeOrActive where p.id = :id")
    int deleteLogicById(@Param("id") Long id, @Param("inativeOrActive") boolean inativeOrActive);

}
