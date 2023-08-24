package br.com.stoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;

    @NotEmpty(message = "Name {field.not.blank}")
    @Size(min = 2, max = 150, message = "{field.size}")
    private String name;

    private Boolean active = true;

}
