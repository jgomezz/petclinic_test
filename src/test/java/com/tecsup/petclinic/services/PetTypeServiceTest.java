package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetTypeDTO;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PetTypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    /** BUSCAR POR ID */
    @Test
    public void testFindPetTypeById() {

        Integer ID = 1;
        String NAME_EXPECTED = "cat";

        PetTypeDTO petType = null;

        try {
            petType = petTypeService.findById(ID);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("PetType encontrado: {}", petType);
        assertEquals(NAME_EXPECTED, petType.getName());
    }

    /** BUSCAR POR NOMBRE */
    @Test
    public void testFindPetTypeByName() {

        String FIND_NAME = "dog";
        int SIZE_EXPECTED = 1;

        List<PetTypeDTO> petTypes = petTypeService.findByName(FIND_NAME);

        log.info("PetTypes encontrados: {}", petTypes);
        assertEquals(SIZE_EXPECTED, petTypes.size());
    }

    /** CREAR */
    @Test
    public void testCreatePetType() {

        String NAME = "fish";
        String DESCRIPTION = "Freshwater fish";
        Boolean ACTIVE = true;
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 5;
        String CARE_LEVEL = "low";

        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(ACTIVE)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        PetTypeDTO newPetType = petTypeService.create(petTypeDTO);

        log.info("PetType creado: {}", newPetType);

        assertNotNull(newPetType.getId());
        assertEquals(NAME, newPetType.getName());
        assertEquals(DESCRIPTION, newPetType.getDescription());
        assertEquals(ACTIVE, newPetType.getActive());
    }

    /** ACTUALIZAR */
    @Test
    public void testUpdatePetType() {

        String NAME = "parrot";
        String DESCRIPTION = "Tropical bird";
        Boolean ACTIVE = true;
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 20;
        String CARE_LEVEL = "medium";

        String UP_NAME = "parrot updated";
        String UP_DESCRIPTION = "Exotic tropical bird";
        String UP_CARE_LEVEL = "high";

        // Crear
        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(ACTIVE)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        PetTypeDTO created = petTypeService.create(petTypeDTO);
        log.info("PetType creado: {}", created);

        // Actualizar
        created.setName(UP_NAME);
        created.setDescription(UP_DESCRIPTION);
        created.setCareLevel(UP_CARE_LEVEL);

        PetTypeDTO updated = petTypeService.update(created);
        log.info("PetType actualizado: {}", updated);

        assertEquals(UP_NAME, updated.getName());
        assertEquals(UP_DESCRIPTION, updated.getDescription());
        assertEquals(UP_CARE_LEVEL, updated.getCareLevel());
    }

    /** ELIMINAR */
    @Test
    public void testDeletePetType() {

        String NAME = "gecko";
        String DESCRIPTION = "Small lizard";
        Boolean ACTIVE = true;
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 8;
        String CARE_LEVEL = "medium";

        // Crear
        PetTypeDTO petTypeDTO = PetTypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(ACTIVE)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        PetTypeDTO created = petTypeService.create(petTypeDTO);
        log.info("PetType creado para eliminar: {}", created);

        // Eliminar
        try {
            petTypeService.delete(created.getId());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        // Verificar que ya no existe
        try {
            petTypeService.findById(created.getId());
            fail("Debería haber lanzado PetTypeNotFoundException");
        } catch (PetTypeNotFoundException e) {
            assertTrue(true);
        }

        log.info("PetType eliminado correctamente con ID: {}", created.getId());
    }
}