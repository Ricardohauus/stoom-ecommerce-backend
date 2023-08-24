package br.com.stoom.service.impl;

import br.com.stoom.dto.FilterProductDTO;
import br.com.stoom.dto.ProductDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Product;
import br.com.stoom.repository.ProductRepository;
import br.com.stoom.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private static final boolean ACTIVE = true;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        findByName(productDTO);
        var productSaved = productRepository.save(this.modelMapper.map(productDTO, Product.class));
        return this.modelMapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, Long id) {
        productDTO.setId(id);
        findByName(productDTO);
        var existsProduct = findById(id);
        productDTO.setCreatedAt(existsProduct.getCreatedAt());
        productDTO.setActive(existsProduct.getActive());

        var productSaved = productRepository.save(this.modelMapper.map(productDTO, Product.class));
        return this.modelMapper.map(productSaved, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> findAll(FilterProductDTO filterProductDTO) {
        var products = productRepository.findAllByActiveAndCategory_NameContainsAndCategory_ActiveAndBrand_NameContainsAndBrand_ActiveAndNameContains(ACTIVE, filterProductDTO.getCategoryName(), ACTIVE, filterProductDTO.getBrandName(), ACTIVE, filterProductDTO.getProductName());
        return products.stream().map(user -> this.modelMapper.map(user, ProductDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        existsById(id);
        return this.modelMapper.map(productRepository.findByIdAndActive(id, ACTIVE), ProductDTO.class);
    }

    @Override
    public Boolean inativeOrActive(Long id, boolean inativeOrActive) {
        return productRepository.deleteLogicById(id, inativeOrActive) > 0;
    }

    private void existsById(Long id) {
        if (!productRepository.existsByIdAndActive(id, ACTIVE)) {
            throw new ResourceNotAcceptableException("No record found!");
        }
    }

    private void findByName(ProductDTO productDTO) {
        var productExists = productRepository.findByNameAndActive(productDTO.getName(), ACTIVE);
        if (productExists != null && productDTO.getId() == null ||
                productExists != null && productDTO.getId() != null && !productExists.getId().equals(productDTO.getId()))
            throw new ResourceNotAcceptableException("Product already registered!");
    }

}
