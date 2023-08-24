package br.com.stoom.controller;

import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return new ResponseEntity<>(this.categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO category) {
        return new ResponseEntity<>(this.categoryService.create(category), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> update(@RequestBody @Valid CategoryDTO category, @PathVariable("id") Long id) {
        return new ResponseEntity<>(this.categoryService.update(category, id), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.categoryService.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "active/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> active(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.categoryService.inativeOrActive(id, true), HttpStatus.OK);
    }

    @PutMapping(value = "inative/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> inative(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.categoryService.inativeOrActive(id,false), HttpStatus.OK);
    }

}
