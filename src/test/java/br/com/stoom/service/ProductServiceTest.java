package br.com.stoom.service;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.dto.FilterProductDTO;
import br.com.stoom.dto.ProductDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Brand;
import br.com.stoom.model.Category;
import br.com.stoom.model.Product;
import br.com.stoom.repository.ProductRepository;
import br.com.stoom.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl brandServiceImpl;

    @Test
    public void when_save_brand() {

        var productDTO = new ProductDTO(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());

        when(brandRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(product);
        when(brandRepository.save(any(Product.class))).thenReturn(product);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        var brandSaved = brandServiceImpl.create(productDTO);

        assertThat(brandSaved.getName()).isSameAs(productDTO.getName());
        assertThat(brandSaved.getId()).isSameAs(productDTO.getId());
        assertThat(brandSaved.getActive()).isSameAs(productDTO.getActive());
    }


    @Test
    public void when_update_brand() {

        var productDTO = new ProductDTO(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());

        when(brandRepository.findByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(product);
        when(brandRepository.save(any(Product.class))).thenReturn(product);
        when(brandRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        var brandSaved = brandServiceImpl.update(productDTO, productDTO.getId());

        assertThat(brandSaved.getName()).isSameAs(productDTO.getName());
        assertThat(brandSaved.getId()).isSameAs(productDTO.getId());
        assertThat(brandSaved.getActive()).isSameAs(productDTO.getActive());
    }

    @Test
    public void when_findAll_brand() {
        var productDTO = new ProductDTO(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());
        List<Product> brandList = List.of(new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand()));
        var filterProductDTO = new FilterProductDTO();
        when(brandRepository.findAllByActiveAndCategory_NameContainsAndCategory_ActiveAndBrand_NameContainsAndBrand_ActiveAndNameContains(anyBoolean(), anyString(), anyBoolean(), anyString(), anyBoolean(), anyString())).thenReturn(brandList);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        var brandListFind = brandServiceImpl.findAll(filterProductDTO);

        assertThat(brandListFind.size()).isSameAs(1);
    }

    @Test
    public void when_inative_or_ative_brand() {
        when(brandRepository.deleteLogicById(anyLong(), eq(Boolean.TRUE))).thenReturn(1);

        var inative = brandServiceImpl.inativeOrActive(1L, Boolean.TRUE);

        assertThat(inative).isSameAs(Boolean.TRUE);
    }

    @Test
    public void when_find_by_id_brand() {
        var productDTO = new ProductDTO(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());

        when(brandRepository.findByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(product);
        when(brandRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        var brandFind = brandServiceImpl.findById(1L);

        assertThat(brandFind.getName()).isSameAs(productDTO.getName());
        assertThat(brandFind.getId()).isSameAs(productDTO.getId());
        assertThat(brandFind.getActive()).isSameAs(productDTO.getActive());
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_id_brand() {
        brandServiceImpl.findById(1L);
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_name_brand_duplicate() {
        var productDTO = new ProductDTO(2L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());
        var product = new Product(1L, "Produto", "Description", 10.0, true, LocalDateTime.now(), LocalDateTime.now(), new Category(), new Brand());

        when(brandRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(product);
        brandServiceImpl.create(productDTO);
    }

}
