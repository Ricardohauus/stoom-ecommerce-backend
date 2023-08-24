package br.com.stoom.controller;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.service.BrandService;
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
@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @MockBean
    private BrandService brandService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_created_brand() throws Exception {
        BrandDTO brandDTO = new BrandDTO(1L, "Samsung", true);

        when(brandService.create(any(BrandDTO.class))).thenReturn(brandDTO);

        mockMvc.perform(post("/brands")
                .content(objectMapper.writeValueAsString(brandDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(brandDTO.getName()));
    }

    @Test
    public void should_updated_brand() throws Exception {
        BrandDTO brandDTO = new BrandDTO(1L, "Samsung", true);

        when(brandService.update(any(BrandDTO.class), any())).thenReturn(brandDTO);

        mockMvc.perform(patch("/brands/{id}", brandDTO.getId())
                .content(objectMapper.writeValueAsString(brandDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(brandDTO.getName()));
    }

    @Test
    public void should_list_all() throws Exception {
        List<BrandDTO> brandDTOList = List.of(new BrandDTO(1L, "Samsung", true), new BrandDTO(2L, "LG", true));

        when(brandService.findAll()).thenReturn(brandDTOList);

        mockMvc.perform(get("/brands")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Samsung"))
                .andExpect(jsonPath("$[1].name").value("LG"));
    }

    @Test
    public void should_find_one() throws Exception {
        BrandDTO brandDTO = new BrandDTO(1L, "Samsung", true);

        when(brandService.findById(any())).thenReturn(brandDTO);

        mockMvc.perform(get("/brands/findById/{id}", brandDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung"));
    }

    @Test
    public void should_inative_brand() throws Exception {
        when(brandService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/brands/inative/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

    @Test
    public void should_active_brand() throws Exception {
        when(brandService.inativeOrActive(anyLong(), anyBoolean())).thenReturn(Boolean.TRUE);
        mockMvc.perform(put("/brands/active/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Boolean.TRUE));
    }

}
