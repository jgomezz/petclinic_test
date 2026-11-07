package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.dtos.PetTypeCountDTO;
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

    @Autowired
    private PetService petService;

    private PetTypeDTO createType(String suffix, boolean active) {
        PetTypeDTO dto = PetTypeDTO.builder()
                .name("type" + suffix)
                .description("Description " + suffix)
                .active(active)
                .sizeCategory("small")
                .averageLifespan(10)
                .careLevel("medium")
                .build();
        return petTypeService.create(dto);
    }

    @Test
    public void testCreateType() {
        PetTypeDTO created = createType("created", true);
        log.info("TYPE CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("typecreated", created.getName());
        assertTrue(created.getActive());
    }

    @Test
    public void testUpdateType() throws PetTypeNotFoundException {
        PetTypeDTO created = createType("update", true);

        created.setDescription("Updated description");
        created.setCareLevel("low");

        PetTypeDTO updated = petTypeService.update(created);

        assertEquals("Updated description", updated.getDescription());
        assertEquals("low", updated.getCareLevel());
    }

    @Test
    public void testFindTypeById() throws PetTypeNotFoundException {
        PetTypeDTO created = createType("find", true);

        PetTypeDTO found = petTypeService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("typefind", found.getName());
    }

    @Test
    public void testDeleteType() throws PetTypeNotFoundException {
        PetTypeDTO created = createType("delete", true);

        petTypeService.delete(created.getId());

        assertThrows(PetTypeNotFoundException.class,
                () -> petTypeService.findById(created.getId()));
    }

    @Test
    public void testFindTypeById_NotFound() {
        assertThrows(PetTypeNotFoundException.class,
                () -> petTypeService.findById(999999));
    }

    @Test
    public void testDeleteType_NotFound() {
        assertThrows(PetTypeNotFoundException.class,
                () -> petTypeService.delete(999999));
    }

    @Test
    public void testGetPetCountByType() {
        List<PetTypeCountDTO> report = petTypeService.getPetCountByType();

        assertFalse(report.isEmpty());
        assertTrue(report.stream().allMatch(row -> row.getPetCount() >= 0));
        assertTrue(report.stream().anyMatch(row -> "cat".equals(row.getTypeName())));
    }

    @Test
    public void testGetPetCountByType_ExcludeInactive() {
        List<PetTypeCountDTO> report = petTypeService.getPetCountByType();

        // "snake" is inactive in data.sql and must not appear in the report
        assertTrue(report.stream().noneMatch(row -> "snake".equals(row.getTypeName())));
    }

    @Test
    public void testGetPetCountByType_WithNewType() throws Exception {
        PetTypeDTO type = createType("report", true);

        PetDTO pet = PetDTO.builder()
                .name("ReportPet")
                .ownerId(1)
                .typeId(type.getId())
                .build();
        PetDTO createdPet = petService.create(pet);

        try {
            List<PetTypeCountDTO> report = petTypeService.getPetCountByType();
            PetTypeCountDTO row = report.stream()
                    .filter(r -> r.getTypeId().equals(type.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Type not present in report"));

            assertEquals(1, row.getPetCount());
            assertEquals("typereport", row.getTypeName());
        } finally {
            petService.delete(createdPet.getId());
            petTypeService.delete(type.getId());
        }
    }
}
