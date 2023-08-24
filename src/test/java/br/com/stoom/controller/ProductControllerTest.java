package br.com.stoom.controller;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.dto.FilterProductDTO;
import br.com.stoom.dto.ProductDTO;
import br.com.stoom.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_created_product() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Produto 1");
        productDTO.setCreatedAt(LocalDateTime.now());
        productDTO.setUpdatedAt(LocalDateTime.now());
        productDTO.setBrand(new BrandDTO());
        productDTO.setCategory(new CategoryDTO());
        productDTO.setDescription("Description Teste");
        productDTO.setPrice(50.0);

        when(productService.create(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    public void should_updated_product() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Produto 1");
        productDTO.setCreatedAt(LocalDateTime.now());
        productDTO.setUpdatedAt(LocalDateTime.now());
        productDTO.setBrand(new BrandDTO());
        productDTO.setCategory(new CategoryDTO());
        productDTO.setDescription("Description Test");
        productDTO.setPrice(50.0);

        when(productService.update(any(ProductDTO.class), any())).thenReturn(productDTO);

        mockMvc.perform(patch("/products/{id}", productDTO.getId())
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    public void should_list_all() throws Exception {
        List<ProductDTO> productDTOList = List.of(new ProductDTO(1L, "Produto 1", "Description Test", 50.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO()), new ProductDTO(1L, "Produto 2", "Description Test 2", 150.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO()));

        FilterProductDTO filterProductDTO = new FilterProductDTO();

        when(productService.findAll(filterProductDTO)).thenReturn(productDTOList);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Produto 1"))
                .andExpect(jsonPath("$[1].name").value("Produto 2"));
    }

    @Test
    public void should_find_one() throws Exception {
        ProductDTO productDTO = new ProductDTO(1L, "Produto 1", "Description Test 1", 150.0, true, LocalDateTime.now(), LocalDateTime.now(), new CategoryDTO(), new BrandDTO());

        when(productService.findById(any())).thenReturn(productDTO);

        mockMvc.perform(get("/products/findById/{id}", productDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    public void should_inative_product() throws Exception {
        when(productService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/products/inative/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

    @Test
    public void should_active_product() throws Exception {
        when(productService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);
        mockMvc.perform(put("/products/active/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

}
