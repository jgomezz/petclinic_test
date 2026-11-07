package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.exceptions.SpecialtyNotAssignedException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Autowired
    private VetSpecialtyRepository vetSpecialtyRepository;

    private VetSpecialtyDTO assign(Integer vetId, Integer specialtyId,
                                   Integer years, LocalDate certification, boolean primary)
            throws DuplicateVetSpecialtyException {
        VetSpecialtyDTO dto = VetSpecialtyDTO.builder()
                .vetId(vetId)
                .specialtyId(specialtyId)
                .yearsExperience(years)
                .certificationDate(certification)
                .isPrimary(primary)
                .notes("test assignment")
                .build();
        return vetSpecialtyService.assignSpecialtyToVet(dto);
    }

    @Test
    public void testAssignSpecialtyToVet() throws DuplicateVetSpecialtyException {
        VetSpecialtyDTO assigned = assign(1, 2, 3, LocalDate.of(2020, 1, 15), false);
        log.info("ASSIGNED: {}", assigned);

        assertEquals(1, assigned.getVetId());
        assertEquals(2, assigned.getSpecialtyId());
        assertEquals(3, assigned.getYearsExperience());
    }

    @Test
    public void testAssignDuplicate_ShouldFail() throws DuplicateVetSpecialtyException {
        assign(1, 3, 2, LocalDate.of(2021, 2, 10), false);

        assertThrows(DuplicateVetSpecialtyException.class,
                () -> assign(1, 3, 4, LocalDate.of(2021, 2, 10), false));
    }

    @Test
    public void testRemoveSpecialtyFromVet() throws Exception {
        assign(5, 2, 1, LocalDate.of(2022, 3, 11), false);

        vetSpecialtyService.removeSpecialtyFromVet(5, 2);

        List<SpecialtyDTO> specialties = vetSpecialtyService.findSpecialtiesByVet(5);
        assertTrue(specialties.stream().noneMatch(s -> s.getId().equals(2)));
    }

    @Test
    public void testFindSpecialtiesByVet() {
        List<SpecialtyDTO> specialties = vetSpecialtyService.findSpecialtiesByVet(2);

        assertFalse(specialties.isEmpty());
        assertTrue(specialties.stream().anyMatch(s -> "radiology".equals(s.getName())));
    }

    @Test
    public void testFindVetsBySpecialty() {
        List<VetDTO> vets = vetSpecialtyService.findVetsBySpecialty(1);

        assertFalse(vets.isEmpty());
        assertTrue(vets.stream().anyMatch(v -> "Leary".equals(v.getLastName())));
    }

    @Test
    public void testFindSpecialtiesByVet_Empty() {
        List<SpecialtyDTO> specialties = vetSpecialtyService.findSpecialtiesByVet(999999);
        assertTrue(specialties.isEmpty());
    }

    @Test
    public void testSetPrimarySpecialty() throws Exception {
        if (!vetSpecialtyRepository.existsById(new VetSpecialtyId(4, 3))) {
            assign(4, 3, 5, LocalDate.of(2019, 4, 5), false);
        }

        VetSpecialtyDTO primary = vetSpecialtyService.setPrimarySpecialty(4, 3);

        assertTrue(primary.getIsPrimary());
        long primaries = vetSpecialtyRepository.findByIdVetId(4).stream()
                .filter(VetSpecialty::getIsPrimary)
                .count();
        assertEquals(1, primaries);
    }

    @Test
    public void testSetPrimarySpecialty_OnlyOnePrimary() throws Exception {
        if (!vetSpecialtyRepository.existsById(new VetSpecialtyId(4, 3))) {
            assign(4, 3, 5, LocalDate.of(2019, 4, 5), false);
        }
        vetSpecialtyService.setPrimarySpecialty(4, 3);

        List<VetSpecialty> assignments = vetSpecialtyRepository.findByIdVetId(4);
        long primaries = assignments.stream().filter(VetSpecialty::getIsPrimary).count();

        assertEquals(1, primaries);
        assertTrue(assignments.stream()
                .anyMatch(a -> a.getId().getSpecialtyId().equals(3) && a.getIsPrimary()));
    }

    @Test
    public void testSetPrimarySpecialty_NotAssigned() {
        assertThrows(SpecialtyNotAssignedException.class,
                () -> vetSpecialtyService.setPrimarySpecialty(6, 1));
    }

    @Test
    public void testFindVetsByMinExperience() {
        List<VetDTO> vets = vetSpecialtyService.findVetsByMinExperience(1, 8);

        assertTrue(vets.stream().anyMatch(v -> "Leary".equals(v.getLastName())));
        assertTrue(vets.stream().noneMatch(v -> "Stevens".equals(v.getLastName())));
    }

    @Test
    public void testUpdateYearsExperience() throws Exception {
        assign(6, 2, 1, LocalDate.of(2023, 6, 1), false);

        VetSpecialtyDTO updated = vetSpecialtyService.updateYearsExperience(6, 2, 12);

        assertEquals(12, updated.getYearsExperience());
    }

    @Test
    public void testUpdateCertificationDate() throws Exception {
        assign(6, 3, 1, LocalDate.of(2023, 6, 1), false);

        LocalDate newDate = LocalDate.of(2024, 7, 20);
        VetSpecialtyDTO updated = vetSpecialtyService.updateCertificationDate(6, 3, newDate);

        assertEquals(newDate, updated.getCertificationDate());
    }
}
