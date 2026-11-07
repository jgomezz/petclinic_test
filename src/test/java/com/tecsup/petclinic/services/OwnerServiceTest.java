package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    private OwnerDTO createOwner(String suffix) {
        OwnerDTO dto = OwnerDTO.builder()
                .firstName("Test")
                .lastName("Owner" + suffix)
                .address("Test Street 123")
                .city("TestCity" + suffix)
                .telephone("900000111")
                .build();
        return ownerService.create(dto);
    }

    @Test
    public void testFindOwnerById() throws OwnerNotFoundException {
        final String LAST_NAME_EXPECTED = "Franklin";
        OwnerDTO owner = ownerService.findById(1);

        assertNotNull(owner);
        assertEquals(LAST_NAME_EXPECTED, owner.getLastName());
    }

    @Test
    public void testCreateOwner() {
        OwnerDTO created = createOwner("Created");
        log.info("OWNER CREATED: {}", created);

        assertNotNull(created.getId());
        assertEquals("OwnerCreated", created.getLastName());
        assertEquals("TestCityCreated", created.getCity());
    }

    @Test
    public void testUpdateOwner() throws OwnerNotFoundException {
        OwnerDTO created = createOwner("Update");

        created.setAddress("New Address 456");
        created.setTelephone("999888777");

        OwnerDTO updated = ownerService.update(created);

        assertEquals("New Address 456", updated.getAddress());
        assertEquals("999888777", updated.getTelephone());
    }

    @Test
    public void testFindByLastName() {
        createOwner("ByLastName");
        List<OwnerDTO> owners = ownerService.findByLastName("OwnerByLastName");

        assertEquals(1, owners.size());
        assertEquals("OwnerByLastName", owners.get(0).getLastName());
    }

    @Test
    public void testFindByCity() {
        createOwner("ByCity");
        List<OwnerDTO> owners = ownerService.findByCity("TestCityByCity");

        assertEquals(1, owners.size());
        assertEquals("TestCityByCity", owners.get(0).getCity());
    }

    @Test
    public void testFindByLastName_Empty() {
        List<OwnerDTO> owners = ownerService.findByLastName("NoExisteOwnerXYZ");
        assertTrue(owners.isEmpty());
    }

    @Test
    public void testDeleteOwner() throws OwnerNotFoundException {
        OwnerDTO created = createOwner("Delete");

        ownerService.delete(created.getId());

        assertThrows(OwnerNotFoundException.class,
                () -> ownerService.findById(created.getId()));
    }

    @Test
    public void testFindOwnerById_NotFound() {
        assertThrows(OwnerNotFoundException.class,
                () -> ownerService.findById(999999));
    }

    @Test
    public void testDeleteOwner_NotFound() {
        assertThrows(OwnerNotFoundException.class,
                () -> ownerService.delete(999999));
    }
}
