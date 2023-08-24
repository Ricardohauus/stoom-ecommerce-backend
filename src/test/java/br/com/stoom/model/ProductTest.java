package br.com.stoom.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class ProductTest {

    @Test
    public void prePersist() {
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());
        product.prePersist();

        LocalDateTime creationTime = product.getCreatedAt();
        LocalDateTime modificationTime = product.getUpdatedAt();

        assertThat(creationTime).isNotNull();
        assertThat(modificationTime).isNotNull();
    }

    @Test
    public void preUpdate() {
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());
        product.prePersist();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Back to work
        }

        product.preUpdate();

        LocalDateTime creationTime = product.getCreatedAt();
        LocalDateTime modificationTime = product.getUpdatedAt();

        assertThat(creationTime).isNotNull();
        assertThat(modificationTime).isNotNull();
        assertThat(modificationTime.isAfter(creationTime));
    }

}
