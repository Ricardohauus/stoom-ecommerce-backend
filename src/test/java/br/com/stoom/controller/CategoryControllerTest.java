package br.com.stoom.controller;

import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_created_category() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Telefonia", true);

        when(categoryService.create(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/categories")
                .content(objectMapper.writeValueAsString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    public void should_updated_category() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Telefonia", true);

        when(categoryService.update(any(CategoryDTO.class), any())).thenReturn(categoryDTO);

        mockMvc.perform(patch("/categories/{id}", categoryDTO.getId())
                .content(objectMapper.writeValueAsString(categoryDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    public void should_list_all() throws Exception {
        List<CategoryDTO> categoryDTOList = List.of(new CategoryDTO(1L, "Telefonia", true), new CategoryDTO(2L, "Moveis", true));

        when(categoryService.findAll()).thenReturn(categoryDTOList);

        mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(categoryDTOList.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(categoryDTOList.get(1).getName()));
    }

    @Test
    public void should_find_one() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Telefonia", true);

        when(categoryService.findById(any())).thenReturn(categoryDTO);

        mockMvc.perform(get("/categories/findById/{id}", categoryDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    public void should_inative_category() throws Exception {
        when(categoryService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/categories/inative/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

    @Test
    public void should_active_category() throws Exception {
        when(categoryService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);
        mockMvc.perform(put("/categories/active/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

}
