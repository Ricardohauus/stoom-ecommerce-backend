package br.com.stoom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(value = {"id", "createdAt", "updatedAt", "active"}, allowGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotEmpty(message = "Name {field.not.blank}")
    @Size(min = 2, max = 150, message = "{field.size}")
    private String name;

    @NotEmpty(message = "Description {field.not.blank}")
    @Size(min = 3, max = 500, message = "{field.size}")
    private String description;

    @NotNull(message = "Price {field.not.null}")
    private Double price;

    private Boolean active = true;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @NotNull(message = "Category {field.not.null}")
    private CategoryDTO category;

    @NotNull(message = "Brand {field.not.null}")
    private BrandDTO brand;


}
