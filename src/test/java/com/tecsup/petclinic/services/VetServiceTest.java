package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testCreateVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Carlos")
                .lastName("Neyra")
                .email("carlos.neyra@petclinic.com")
                .phone("999888777")
                .active(true)
                .build();

        VetDTO newVetDTO = vetService.create(vetDTO);

        log.info("VET CREATED: {}", newVetDTO);

        assertNotNull(newVetDTO);
        assertNotNull(newVetDTO.getId());
        assertEquals("Carlos", newVetDTO.getFirstName());
        assertEquals("Neyra", newVetDTO.getLastName());
        assertEquals("carlos.neyra@petclinic.com", newVetDTO.getEmail());
        assertEquals("999888777", newVetDTO.getPhone());
        assertEquals(Boolean.TRUE, newVetDTO.getActive());
    }

    @Test
    public void testUpdateVet() throws VetNotFoundException {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Luis")
                .lastName("Ramirez")
                .email("luis.ramirez@petclinic.com")
                .phone("111222333")
                .active(true)
                .build();

        VetDTO vetCreated = vetService.create(vetDTO);

        vetCreated.setFirstName("Luis Actualizado");
        vetCreated.setLastName("Ramirez Actualizado");
        vetCreated.setEmail("luis.actualizado@petclinic.com");
        vetCreated.setPhone("444555666");
        vetCreated.setActive(false);

        VetDTO vetUpdated = vetService.update(vetCreated);

        log.info("VET UPDATED: {}", vetUpdated);

        assertNotNull(vetUpdated);
        assertEquals(vetCreated.getId(), vetUpdated.getId());
        assertEquals("Luis Actualizado", vetUpdated.getFirstName());
        assertEquals("Ramirez Actualizado", vetUpdated.getLastName());
        assertEquals("luis.actualizado@petclinic.com", vetUpdated.getEmail());
        assertEquals("444555666", vetUpdated.getPhone());
        assertEquals(Boolean.FALSE, vetUpdated.getActive());
    }

    @Test
    public void testFindVetById() throws VetNotFoundException {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Ana")
                .lastName("Torres")
                .email("ana.torres@petclinic.com")
                .phone("555666777")
                .active(true)
                .build();

        VetDTO vetCreated = vetService.create(vetDTO);

        VetDTO vetFound = vetService.findById(vetCreated.getId());

        log.info("VET FOUND: {}", vetFound);

        assertNotNull(vetFound);
        assertEquals(vetCreated.getId(), vetFound.getId());
        assertEquals("Ana", vetFound.getFirstName());
        assertEquals("Torres", vetFound.getLastName());
        assertEquals("ana.torres@petclinic.com", vetFound.getEmail());
    }

    @Test
    public void testDeleteVet() throws VetNotFoundException {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Pedro")
                .lastName("Delete")
                .email("pedro.delete@petclinic.com")
                .phone("777888999")
                .active(true)
                .build();

        VetDTO vetCreated = vetService.create(vetDTO);

        assertNotNull(vetCreated.getId());

        vetService.delete(vetCreated.getId());

        assertThrows(VetNotFoundException.class, () -> {
            vetService.findById(vetCreated.getId());
        });
    }
}