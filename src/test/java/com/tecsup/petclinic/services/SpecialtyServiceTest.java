package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.InvalidHourRangeException;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    private SpecialtyDTO createSpecialty(String suffix)
            throws InvalidHourRangeException, InvalidScheduleException {
        SpecialtyDTO dto = SpecialtyDTO.builder()
                .name("specialty" + suffix)
                .office("Office" + suffix)
                .openHour(8)
                .closeHour(18)
                .build();
        return specialtyService.create(dto);
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        SpecialtyDTO created = createSpecialty("created");
        log.info("SPECIALTY CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("specialtycreated", created.getName());
        assertEquals(8, created.getOpenHour());
        assertEquals(18, created.getCloseHour());
    }

    @Test
    public void testUpdateSpecialty() throws Exception {
        SpecialtyDTO created = createSpecialty("update");

        created.setOffice("New Office");
        created.setOpenHour(9);
        created.setCloseHour(17);

        SpecialtyDTO updated = specialtyService.update(created);

        assertEquals("New Office", updated.getOffice());
        assertEquals(9, updated.getOpenHour());
        assertEquals(17, updated.getCloseHour());
    }

    @Test
    public void testFindSpecialtyById() throws Exception {
        SpecialtyDTO created = createSpecialty("find");

        SpecialtyDTO found = specialtyService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("specialtyfind", found.getName());
    }

    @Test
    public void testFindByName() throws Exception {
        createSpecialty("byname");
        List<SpecialtyDTO> specialties = specialtyService.findByName("specialtybyname");

        assertEquals(1, specialties.size());
        assertEquals("specialtybyname", specialties.get(0).getName());
    }

    @Test
    public void testFindByOffice() throws Exception {
        createSpecialty("byoffice");
        List<SpecialtyDTO> specialties = specialtyService.findByOffice("Officebyoffice");

        assertEquals(1, specialties.size());
        assertEquals("Officebyoffice", specialties.get(0).getOffice());
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        SpecialtyDTO created = createSpecialty("delete");

        specialtyService.delete(created.getId());

        assertThrows(SpecialtyNotFoundException.class,
                () -> specialtyService.findById(created.getId()));
    }

    @Test
    public void testFindSpecialtyById_NotFound() {
        assertThrows(SpecialtyNotFoundException.class,
                () -> specialtyService.findById(999999));
    }

    @Test
    public void testCreateSpecialty_InvalidSchedule() {
        SpecialtyDTO dto = SpecialtyDTO.builder()
                .name("invalid-schedule")
                .office("Office")
                .openHour(10)
                .closeHour(10)
                .build();

        assertThrows(InvalidScheduleException.class,
                () -> specialtyService.create(dto));
    }

    @Test
    public void testCreateSpecialty_InvalidHourRange() {
        SpecialtyDTO dto = SpecialtyDTO.builder()
                .name("invalid-hour-range")
                .office("Office")
                .openHour(-1)
                .closeHour(25)
                .build();

        assertThrows(InvalidHourRangeException.class,
                () -> specialtyService.create(dto));
    }
}
