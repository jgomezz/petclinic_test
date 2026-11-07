package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    private VisitDTO createVisit(Integer petId, Integer vetId, LocalDate date,
                                 String description, String cost) {
        VisitDTO dto = VisitDTO.builder()
                .petId(petId)
                .vetId(vetId)
                .visitDate(date)
                .description(description)
                .cost(new BigDecimal(cost))
                .build();
        return visitService.create(dto);
    }

    @Test
    public void testCreateVisit() {
        VisitDTO created = createVisit(13, 1, LocalDate.of(2035, 1, 1),
                "created visit", "45.00");
        log.info("VISIT CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals(13, created.getPetId());
        assertEquals(1, created.getVetId());
        assertEquals(0, created.getCost().compareTo(new BigDecimal("45.00")));
    }

    @Test
    public void testUpdateVisit() throws VisitNotFoundException {
        VisitDTO created = createVisit(13, 1, LocalDate.of(2035, 1, 2),
                "before update", "50.00");

        created.setDescription("after update");
        created.setCost(new BigDecimal("75.50"));

        VisitDTO updated = visitService.update(created);

        assertEquals("after update", updated.getDescription());
        assertEquals(0, updated.getCost().compareTo(new BigDecimal("75.50")));
    }

    @Test
    public void testFindVisitById() throws VisitNotFoundException {
        VisitDTO created = createVisit(13, 1, LocalDate.of(2035, 1, 3),
                "find visit", "30.00");

        VisitDTO found = visitService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("find visit", found.getDescription());
    }

    @Test
    public void testFindByPetId() {
        VisitDTO created = createVisit(13, 1, LocalDate.of(2035, 1, 4),
                "visit by pet", "20.00");

        List<VisitDTO> visits = visitService.findByPetId(13);

        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(created.getId())));
    }

    @Test
    public void testFindByVetId() {
        VisitDTO created = createVisit(13, 2, LocalDate.of(2035, 1, 5),
                "visit by vet", "25.00");

        List<VisitDTO> visits = visitService.findByVetId(2);

        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(created.getId())));
    }

    @Test
    public void testDeleteVisit() throws VisitNotFoundException {
        VisitDTO created = createVisit(13, 1, LocalDate.of(2035, 1, 6),
                "visit to delete", "15.00");

        visitService.delete(created.getId());

        assertThrows(VisitNotFoundException.class,
                () -> visitService.findById(created.getId()));
    }

    @Test
    public void testCalculateTotalCostByPet() {
        createVisit(10, 1, LocalDate.of(2035, 2, 1), "cost one", "10.50");
        createVisit(10, 1, LocalDate.of(2035, 2, 2), "cost two", "20.25");

        BigDecimal total = visitService.calculateTotalCostByPet(10);

        assertEquals(0, total.compareTo(new BigDecimal("30.75")));
    }

    @Test
    public void testCalculateTotalCostByPet_NoVisits() {
        BigDecimal total = visitService.calculateTotalCostByPet(11);
        assertEquals(0, total.compareTo(BigDecimal.ZERO));
    }

    @Test
    public void testFindByDateBetween() {
        VisitDTO inRange = createVisit(13, 1, LocalDate.of(2036, 1, 15),
                "in range", "40.00");
        VisitDTO outOfRange = createVisit(13, 1, LocalDate.of(2036, 2, 15),
                "out of range", "40.00");

        List<VisitDTO> visits = visitService.findByDateBetween(
                LocalDate.of(2036, 1, 1), LocalDate.of(2036, 1, 31));

        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(inRange.getId())));
        assertTrue(visits.stream().noneMatch(v -> v.getId().equals(outOfRange.getId())));
    }

    @Test
    public void testFindByVetIdAndDateBetween() {
        VisitDTO created = createVisit(12, 3, LocalDate.of(2037, 5, 10),
                "vet and range", "60.00");

        List<VisitDTO> visits = visitService.findByVetIdAndDateBetween(
                3, LocalDate.of(2037, 5, 1), LocalDate.of(2037, 5, 31));

        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(created.getId())));
        assertTrue(visits.stream().allMatch(v -> v.getVetId().equals(3)));
    }

    @Test
    public void testCountVisitsByPet() {
        long before = visitService.countVisitsByPet(13);

        createVisit(13, 1, LocalDate.of(2038, 1, 1), "counted visit", "10.00");

        assertEquals(before + 1, visitService.countVisitsByPet(13));
    }

    @Test
    public void testFindVisitById_NotFound() {
        assertThrows(VisitNotFoundException.class,
                () -> visitService.findById(999999));
    }
}
