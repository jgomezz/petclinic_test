package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testCreateVet() {
        Vet vet = new Vet();
        vet.setFirstName("Carlos");
        vet.setLastName("Mendoza");

        Vet createdVet = vetService.create(vet);

        assertNotNull(createdVet);
        assertNotNull(createdVet.getId());
        assertEquals("Carlos", createdVet.getFirstName());
        assertEquals("Mendoza", createdVet.getLastName());

        log.info("Vet created: {}", createdVet);
    }

    @Test
    public void testFindVetById() {
        Vet vet = new Vet();
        vet.setFirstName("Ana");
        vet.setLastName("Torres");

        Vet createdVet = vetService.create(vet);

        Optional<Vet> foundVet = vetService.findById(createdVet.getId());

        assertTrue(foundVet.isPresent());
        assertEquals(createdVet.getId(), foundVet.get().getId());
        assertEquals("Ana", foundVet.get().getFirstName());
        assertEquals("Torres", foundVet.get().getLastName());

        log.info("Vet found: {}", foundVet.get());
    }

    @Test
    public void testFindAllVets() {
        Vet vet = new Vet();
        vet.setFirstName("Luis");
        vet.setLastName("Ramirez");

        vetService.create(vet);

        assertFalse(vetService.findAll().isEmpty());

        log.info("Vets list: {}", vetService.findAll());
    }

    @Test
    public void testUpdateVet() {
        Vet vet = new Vet();
        vet.setFirstName("Pedro");
        vet.setLastName("Lopez");

        Vet createdVet = vetService.create(vet);

        Vet updatedData = new Vet();
        updatedData.setFirstName("Pedro Actualizado");
        updatedData.setLastName("Lopez Actualizado");

        Vet updatedVet = vetService.update(createdVet.getId(), updatedData);

        assertNotNull(updatedVet);
        assertEquals(createdVet.getId(), updatedVet.getId());
        assertEquals("Pedro Actualizado", updatedVet.getFirstName());
        assertEquals("Lopez Actualizado", updatedVet.getLastName());

        log.info("Vet updated: {}", updatedVet);
    }

    @Test
    public void testDeleteVet() {
        Vet vet = new Vet();
        vet.setFirstName("Mario");
        vet.setLastName("Salas");

        Vet createdVet = vetService.create(vet);
        Integer id = createdVet.getId();

        vetService.delete(id);

        Optional<Vet> deletedVet = vetService.findById(id);

        assertFalse(deletedVet.isPresent());

        log.info("Vet deleted with id: {}", id);
    }
}