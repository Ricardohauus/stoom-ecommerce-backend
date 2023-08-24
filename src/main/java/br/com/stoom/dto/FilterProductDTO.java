package br.com.stoom.dto;

import lombok.Data;

@Data
public class FilterProductDTO {
    private String productName = "";
    private String brandName = "";
    private String categoryName = "";
}
