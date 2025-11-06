package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    /**
     * Julio: testDeleteSpeciality()
     */
    @Test
    public void testDeleteSpeciality() {

        String NAME = "Neurology";

        Specialty specialty = new Specialty();
        specialty.setName(NAME);

        Specialty newSpecialty = this.specialtyService.create(specialty);
        log.info("CREATED SPECIALTY: " + newSpecialty);

        try {
            this.specialtyService.delete(newSpecialty.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.specialtyService.findById(newSpecialty.getId());
            assertTrue(false);
        } catch (SpecialtyNotFoundException e) {
            assertTrue(true);
        }
    }

    /**
     * Julio: testFindSpecialityById()
     */
    @Test
    public void testFindSpecialityById() {

        String NAME_EXPECTED = "Cardiology";

        Specialty specialty = new Specialty();
        specialty.setName(NAME_EXPECTED);

        Specialty saved = this.specialtyService.create(specialty);

        Specialty found = null;

        try {
            found = this.specialtyService.findById(saved.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(found);
        assertEquals(NAME_EXPECTED, found.getName());
    }
}
