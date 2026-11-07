package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    private VetDTO createVet(String suffix) {
        VetDTO dto = VetDTO.builder()
                .firstName("Test")
                .lastName("Vet" + suffix)
                .email("vet" + suffix + "@petclinic.com")
                .phone("900000000")
                .active(true)
                .build();
        return vetService.create(dto);
    }

    @Test
    public void testFindVetById() throws VetNotFoundException {
        final String LAST_NAME_EXPECTED = "Carter";
        VetDTO vet = vetService.findById(1);
        assertNotNull(vet);
        assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
    }

    @Test
    public void testCreateVet() {
        VetDTO created = createVet("Created");
        log.info("VET CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("VetCreated", created.getLastName());
        assertEquals("Test", created.getFirstName());
        assertTrue(created.getActive());
    }

    @Test
    public void testUpdateVet() throws VetNotFoundException {
        VetDTO created = createVet("Update");

        created.setPhone("111222333");
        created.setEmail("updated@petclinic.com");

        VetDTO updated = vetService.update(created);

        assertEquals("111222333", updated.getPhone());
        assertEquals("updated@petclinic.com", updated.getEmail());
    }

    @Test
    public void testFindByLastName() {
        createVet("ByLastName");
        List<VetDTO> vets = vetService.findByLastName("VetByLastName");
        assertEquals(1, vets.size());
    }

    @Test
    public void testDeactivateVet() throws VetNotFoundException {
        VetDTO created = createVet("Deactivate");

        VetDTO deactivated = vetService.deactivate(created.getId());

        assertFalse(deactivated.getActive());
        assertFalse(vetService.findById(created.getId()).getActive());
    }

    @Test
    public void testReactivateVet() throws VetNotFoundException {
        VetDTO created = createVet("Reactivate");
        vetService.deactivate(created.getId());

        VetDTO reactivated = vetService.reactivate(created.getId());

        assertTrue(reactivated.getActive());
    }

    @Test
    public void testFindActiveVets() {
        List<VetDTO> activeVets = vetService.findActiveVets();
        assertFalse(activeVets.isEmpty());
        assertTrue(activeVets.stream().allMatch(VetDTO::getActive));
    }

    @Test
    public void testFindInactiveVets() {
        List<VetDTO> inactiveVets = vetService.findInactiveVets();
        assertTrue(inactiveVets.stream().noneMatch(VetDTO::getActive));
    }

    @Test
    public void testDeleteVet() throws VetNotFoundException {
        VetDTO created = createVet("Delete");

        vetService.delete(created.getId());

        assertThrows(VetNotFoundException.class,
                () -> vetService.findById(created.getId()));
    }

    @Test
    public void testFindVetById_NotFound() {
        assertThrows(VetNotFoundException.class,
                () -> vetService.findById(999999));
    }

    @Test
    public void testDeactivateVet_NotFound() {
        assertThrows(VetNotFoundException.class,
                () -> vetService.deactivate(999999));
    }
}
