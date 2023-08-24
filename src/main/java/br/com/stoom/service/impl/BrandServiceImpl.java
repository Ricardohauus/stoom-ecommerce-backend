package br.com.stoom.service.impl;

import br.com.stoom.dto.BrandDTO;
import br.com.stoom.dto.CategoryDTO;
import br.com.stoom.error.ResourceNotAcceptableException;
import br.com.stoom.model.Brand;
import br.com.stoom.repository.BrandRepository;
import br.com.stoom.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrandServiceImpl implements BrandService {

    private static final boolean ACTIVE = true;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO create(BrandDTO brandDTO) {
        findByName(brandDTO);
        var categorySaved = brandRepository.save(this.modelMapper.map(brandDTO, Brand.class));
        return this.modelMapper.map(categorySaved, BrandDTO.class);
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO, Long id) {
        brandDTO.setId(id);
        findByName(brandDTO);
        existsById(id);
        var brandSaved = brandRepository.save(this.modelMapper.map(brandDTO, Brand.class));
        return this.modelMapper.map(brandSaved, BrandDTO.class);
    }

    @Override
    public List<BrandDTO> findAll() {
        var brands = brandRepository.findAllByActive(ACTIVE);
        return brands.stream().map(brand -> this.modelMapper.map(brand, BrandDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BrandDTO findById(Long id) {
        existsById(id);
        return this.modelMapper.map(brandRepository.findByIdAndActive(id, ACTIVE), BrandDTO.class);
    }

    @Override
    public Boolean inativeOrActive(Long id, boolean inativeOrActive) {
        return brandRepository.deleteLogicById(id, inativeOrActive) > 0;
    }

    private void existsById(Long id) {
        if (!brandRepository.existsByIdAndActive(id, ACTIVE)) {
            throw new ResourceNotAcceptableException("No record found!");
        }
    }

    private void findByName(BrandDTO brandDTO) {
        var brandExists = brandRepository.findByNameAndActive(brandDTO.getName(), ACTIVE);
        if (brandExists != null && brandDTO.getId() == null ||
                brandExists != null && brandDTO.getId() != null && !brandExists.getId().equals(brandDTO.getId()))
            throw new ResourceNotAcceptableException("Brand already registered!");
    }
}
