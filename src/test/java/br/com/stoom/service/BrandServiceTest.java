package br.com.stoom.service;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Brand;
import br.com.stoom.repository.BrandRepository;
import br.com.stoom.service.impl.BrandServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BrandServiceImpl brandServiceImpl;

    @Test
    public void when_save_brand() {

        var brandDTO = new BrandDTO(1L, "Samsung", true);
        var brand = new Brand(1L, "Samsung", true);

        when(brandRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(brand);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(modelMapper.map(brandDTO, Brand.class)).thenReturn(brand);
        when(modelMapper.map(brand, BrandDTO.class)).thenReturn(brandDTO);

        var brandSaved = brandServiceImpl.create(brandDTO);

        assertThat(brandSaved.getName()).isSameAs(brandDTO.getName());
        assertThat(brandSaved.getId()).isSameAs(brandDTO.getId());
        assertThat(brandSaved.getActive()).isSameAs(brandDTO.getActive());
    }


    @Test
    public void when_update_brand() {

        var brandDTO = new BrandDTO(1L, "Samsung", true);
        var brand = new Brand(1L, "Samsung", true);

        when(brandRepository.findByNameAndActive(anyString(), eq(Boolean.TRUE))).thenReturn(brand);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(brandRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(brandDTO, Brand.class)).thenReturn(brand);
        when(modelMapper.map(brand, BrandDTO.class)).thenReturn(brandDTO);

        var brandSaved = brandServiceImpl.update(brandDTO, brandDTO.getId());

        assertThat(brandSaved.getName()).isSameAs(brandDTO.getName());
        assertThat(brandSaved.getId()).isSameAs(brandDTO.getId());
        assertThat(brandSaved.getActive()).isSameAs(brandDTO.getActive());
    }

    @Test
    public void when_findAll_brand() {
        var brandDTO = new BrandDTO(1L, "Samsung", true);
        var brand = new Brand(1L, "Samsung", true);
        List<BrandDTO> brandDTOList = List.of(brandDTO);
        List<Brand> brandList = List.of(new Brand(1L, "Samsung", true));

        when(brandRepository.findAllByActive(eq(Boolean.TRUE))).thenReturn(brandList);
        when(modelMapper.map(brand, BrandDTO.class)).thenReturn(brandDTO);

        var brandListFind = brandServiceImpl.findAll();

        assertThat(brandListFind.size()).isSameAs(1);
    }

    @Test
    public void when_inative_or_ative_brand() {
        when(brandRepository.deleteLogicById(anyLong(), eq(Boolean.TRUE))).thenReturn(1);

        var inative = brandServiceImpl.inativeOrActive(1L,Boolean.TRUE);

        assertThat(inative).isSameAs(Boolean.TRUE);
    }

    @Test
    public void when_find_by_id_brand() {
        var brand = new Brand(1L, "Samsung", true);
        var brandDTO = new BrandDTO(1L, "Samsung", true);

        when(brandRepository.findByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(brand);
        when(brandRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(brand, BrandDTO.class)).thenReturn(brandDTO);

        var brandFind = brandServiceImpl.findById(1L);

        assertThat(brandFind.getName()).isSameAs(brandDTO.getName());
        assertThat(brandFind.getId()).isSameAs(brandDTO.getId());
        assertThat(brandFind.getActive()).isSameAs(brandDTO.getActive());
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_id_brand() {
        brandServiceImpl.findById(1L);
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_name_brand_duplicate() {
        var brandDTO = new BrandDTO(2L, "Samsung", true);
        var brand = new Brand(1L, "Samsung", true);

        when(brandRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(brand);
        brandServiceImpl.create(brandDTO);
    }

}
