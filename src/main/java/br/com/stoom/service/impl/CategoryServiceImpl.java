package br.com.stoom.service.impl;

import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Category;
import br.com.stoom.repository.CategoryRepository;
import br.com.stoom.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final boolean ACTIVE = true;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        findByName(categoryDTO);
        var categorySaved = categoryRepository.save(this.modelMapper.map(categoryDTO, Category.class));
        return this.modelMapper.map(categorySaved, CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Long id) {
        categoryDTO.setId(id);
        findByName(categoryDTO);
        existsById(id);
        var categorySaved = categoryRepository.save(this.modelMapper.map(categoryDTO, Category.class));
        return this.modelMapper.map(categorySaved, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> findAll() {
        var categories = categoryRepository.findAllByActive(ACTIVE);
        return categories.stream().map(user -> this.modelMapper.map(user, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        existsById(id);
        return this.modelMapper.map(categoryRepository.findByIdAndActive(id, ACTIVE), CategoryDTO.class);
    }

    @Override
    public Boolean inativeOrActive(Long id, boolean inativeOrActive) {
        return categoryRepository.deleteLogicById(id, inativeOrActive) > 0;
    }

    private void existsById(Long id) {
        if (!categoryRepository.existsByIdAndActive(id, ACTIVE)) {
            throw new ResourceNotAcceptableException("No record found!");
        }
    }

    private void findByName(CategoryDTO categoryDTO) {
        var categoryExists = categoryRepository.findByNameAndActive(categoryDTO.getName(), ACTIVE);
        if (categoryExists != null && categoryDTO.getId() == null ||
                categoryExists != null && categoryDTO.getId() != null && !categoryExists.getId().equals(categoryDTO.getId()))
            throw new ResourceNotAcceptableException("Product already registered!");
    }

}
