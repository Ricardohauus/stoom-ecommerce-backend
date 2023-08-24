package br.com.stoom.service;

import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Category;
import br.com.stoom.repository.CategoryRepository;
import br.com.stoom.service.impl.CategoryServiceImpl;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    public void when_save_category() {

        var categoryDTO = new CategoryDTO(1L, "Samsung", true);
        var category = new Category(1L, "Samsung", true);

        when(categoryRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(modelMapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        var categorySaved = categoryServiceImpl.create(categoryDTO);

        assertThat(categorySaved.getName()).isSameAs(categoryDTO.getName());
        assertThat(categorySaved.getId()).isSameAs(categoryDTO.getId());
        assertThat(categorySaved.getActive()).isSameAs(categoryDTO.getActive());
    }


    @Test
    public void when_update_category() {

        var categoryDTO = new CategoryDTO(1L, "Samsung", true);
        var category = new Category(1L, "Samsung", true);

        when(categoryRepository.findByNameAndActive(anyString(), eq(Boolean.TRUE))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        var categorySaved = categoryServiceImpl.update(categoryDTO, categoryDTO.getId());

        assertThat(categorySaved.getName()).isSameAs(categoryDTO.getName());
        assertThat(categorySaved.getId()).isSameAs(categoryDTO.getId());
        assertThat(categorySaved.getActive()).isSameAs(categoryDTO.getActive());
    }

    @Test
    public void when_findAll_category() {
        var categoryDTO = new CategoryDTO(1L, "Samsung", true);
        var category = new Category(1L, "Samsung", true);
        List<CategoryDTO> categoryDTOList = List.of(categoryDTO);
        List<Category> categoryList = List.of(new Category(1L, "Samsung", true));

        when(categoryRepository.findAllByActive(eq(Boolean.TRUE))).thenReturn(categoryList);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        var categoryListFind = categoryServiceImpl.findAll();

        assertThat(categoryListFind.size()).isSameAs(1);
    }

    @Test
    public void when_inative_or_ative_category() {
        when(categoryRepository.deleteLogicById(anyLong(), eq(Boolean.TRUE))).thenReturn(1);

        var inative = categoryServiceImpl.inativeOrActive(1L, Boolean.TRUE);

        assertThat(inative).isSameAs(Boolean.TRUE);
    }

    @Test
    public void when_find_by_id_category() {
        var category = new Category(1L, "Samsung", true);
        var categoryDTO = new CategoryDTO(1L, "Samsung", true);

        when(categoryRepository.findByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(category);
        when(categoryRepository.existsByIdAndActive(anyLong(), eq(Boolean.TRUE))).thenReturn(Boolean.TRUE);
        when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        var categoryFind = categoryServiceImpl.findById(1L);

        assertThat(categoryFind.getName()).isSameAs(categoryDTO.getName());
        assertThat(categoryFind.getId()).isSameAs(categoryDTO.getId());
        assertThat(categoryFind.getActive()).isSameAs(categoryDTO.getActive());
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_id_category() {
        categoryServiceImpl.findById(1L);
    }

    @Test(expected = ResourceNotAcceptableException.class)
    public void when_exception_thrown_find_by_name_category_duplicate() {
        var categoryDTO = new CategoryDTO(2L, "Samsung", true);
        var category = new Category(1L, "Samsung", true);

        when(categoryRepository.findByNameAndActive(anyString(), anyBoolean())).thenReturn(category);
        categoryServiceImpl.create(categoryDTO);
    }

}
