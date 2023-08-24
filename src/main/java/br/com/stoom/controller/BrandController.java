package br.com.stoom.controller;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BrandDTO>> findAll() {
        return new ResponseEntity<>(this.brandService.findAll(), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDTO> create(@RequestBody BrandDTO brandDTO) {
        return new ResponseEntity<>(this.brandService.create(brandDTO), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDTO> update(@RequestBody @Valid BrandDTO brandDTO, @PathVariable("id") Long id) {
        return new ResponseEntity<>(this.brandService.update(brandDTO, id), HttpStatus.OK);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandDTO> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.brandService.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "active/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> active(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.brandService.inativeOrActive(id, true), HttpStatus.OK);
    }

    @PutMapping(value = "inative/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> inative(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.brandService.inativeOrActive(id,false), HttpStatus.OK);
    }

}
