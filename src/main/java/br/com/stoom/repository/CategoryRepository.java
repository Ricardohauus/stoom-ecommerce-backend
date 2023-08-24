package br.com.stoom.repository;

import br.com.stoom.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByNameAndActive(String name, boolean active);

    boolean existsByIdAndActive(Long id, boolean active);

    Category findByIdAndActive(Long id, boolean active);

    List<Category> findAllByActive(boolean active);

    @Transactional
    @Modifying
    @Query("update Category p set p.active = :inativeOrActive where p.id = :id")
    int deleteLogicById(@Param("id") Long id, @Param("inativeOrActive") boolean inativeOrActive);



}
