package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeCountDTO;
import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.mappers.PetTypeMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetTypeServiceImpl implements PetTypeService {

    private final PetTypeRepository petTypeRepository;
    private final PetRepository petRepository;
    private final PetTypeMapper petTypeMapper;

    @Override
    public PetTypeDTO create(PetTypeDTO petTypeDTO) {
        PetType petType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(petType);
    }

    @Override
    public PetTypeDTO update(PetTypeDTO petTypeDTO) {
        PetType petType = petTypeRepository.save(petTypeMapper.mapToEntity(petTypeDTO));
        return petTypeMapper.mapToDto(petType);
    }

    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {
        PetTypeDTO petType = findById(id);
        petTypeRepository.delete(petTypeMapper.mapToEntity(petType));
    }

    @Override
    public PetTypeDTO findById(Integer id) throws PetTypeNotFoundException {
        return petTypeRepository.findById(id)
                .map(petTypeMapper::mapToDto)
                .orElseThrow(() -> new PetTypeNotFoundException("Pet type record not found...!"));
    }

    @Override
    public List<PetTypeDTO> findByName(String name) {
        return petTypeRepository.findByName(name).stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetTypeDTO> findAll() {
        return petTypeRepository.findAll().stream()
                .map(petTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Report of pets grouped by active pet type. Inactive types are excluded.
     */
    @Override
    public List<PetTypeCountDTO> getPetCountByType() {
        List<PetType> types = petTypeRepository.findByActiveTrue();
        types.forEach(type -> log.info("{}", type));
        return types.stream()
                .map(type -> new PetTypeCountDTO(
                        type.getId(),
                        type.getName(),
                        petRepository.countByTypeId(type.getId())))
                .collect(Collectors.toList());
    }
}
