package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.entities.PetType;
import org.springframework.stereotype.Component;

@Component
public class PetTypeMapper {

    public PetType mapToEntity(PetTypeDTO petTypeDTO) {
        PetType petType = new PetType();
        petType.setId(petTypeDTO.getId());
        petType.setName(petTypeDTO.getName());
        petType.setDescription(petTypeDTO.getDescription());
        petType.setActive(petTypeDTO.getActive());
        petType.setSizeCategory(petTypeDTO.getSizeCategory());
        petType.setAverageLifespan(petTypeDTO.getAverageLifespan());
        petType.setCareLevel(petTypeDTO.getCareLevel());
        return petType;
    }

    public PetTypeDTO mapToDto(PetType petType) {
        PetTypeDTO petTypeDTO = new PetTypeDTO();
        petTypeDTO.setId(petType.getId());
        petTypeDTO.setName(petType.getName());
        petTypeDTO.setDescription(petType.getDescription());
        petTypeDTO.setActive(petType.getActive());
        petTypeDTO.setSizeCategory(petType.getSizeCategory());
        petTypeDTO.setAverageLifespan(petType.getAverageLifespan());
        petTypeDTO.setCareLevel(petType.getCareLevel());
        return petTypeDTO;
    }
}