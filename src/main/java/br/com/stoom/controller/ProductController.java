package br.com.stoom.controller;

import br.com.stoom.dto.FilterProductDTO;
import br.com.stoom.dto.ProductDTO;
import br.com.stoom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDTO>> findAll(@ModelAttribute FilterProductDTO filterProductDTO) {
        return new ResponseEntity<>(this.productService.findAll(filterProductDTO), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        return new ResponseEntity<>(this.productService.create(productDTO), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO productDTO, @PathVariable("id") Long id) {
        return new ResponseEntity<>(this.productService.update(productDTO, id), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "active/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> active(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.productService.inativeOrActive(id, true), HttpStatus.OK);
    }

    @PutMapping(value = "inative/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> inative(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.productService.inativeOrActive(id, false), HttpStatus.OK);
    }

}
