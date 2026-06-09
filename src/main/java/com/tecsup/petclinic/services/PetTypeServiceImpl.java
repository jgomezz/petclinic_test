package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mappers.PetTypeMapper;
import com.tecsup.petclinic.repositories.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetTypeServiceImpl implements PetTypeService {

    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;

    @Override
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {
        PetType newPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(newPetType);
    }

    @Override
    public PetTypeDTO update(PetTypeDTO petTypeDTO) {
        PetType updatedPetType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(updatedPetType);
    }

    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {
        PetTypeDTO petType = findById(id);
        petTypeRepository.delete(petTypeMapper.mapToEntity(petType));
    }

    @Override
    public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {
        Optional<PetType> petType = petTypeRepository.findById(id);
        if (!petType.isPresent())
            throw new PetTypeNotFoundException("Tipo de mascota no encontrado...!");
        return petTypeMapper.mapToDto(petType.get());
    }

    @Override
    public List<PetTypeDTO> findByName(String name) {
        List<PetType> petTypes = petTypeRepository.findByName(name);
        petTypes.forEach(pt -> log.info("{}", pt));
        return petTypes.stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetType> findAll() {
        return petTypeRepository.findAll();
    }
}