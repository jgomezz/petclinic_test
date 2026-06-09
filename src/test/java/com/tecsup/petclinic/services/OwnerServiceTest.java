package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testCreateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Juan");
		owner.setLastName("Perez");
		owner.setAddress("Av. Lima 123");
		owner.setCity("Lima");
		owner.setTelephone("987654321");

		Owner createdOwner = ownerService.create(owner);

		assertNotNull(createdOwner);
		assertNotNull(createdOwner.getId());
		assertEquals("Juan", createdOwner.getFirstName());
		assertEquals("Perez", createdOwner.getLastName());
		assertEquals("Av. Lima 123", createdOwner.getAddress());
		assertEquals("Lima", createdOwner.getCity());
		assertEquals("987654321", createdOwner.getTelephone());

		log.info("Owner created: {}", createdOwner);
	}

	@Test
	public void testFindOwnerById() {
		Owner owner = new Owner();
		owner.setFirstName("Maria");
		owner.setLastName("Gomez");
		owner.setAddress("Calle Los Pinos 456");
		owner.setCity("Arequipa");
		owner.setTelephone("912345678");

		Owner createdOwner = ownerService.create(owner);

		Optional<Owner> foundOwner = ownerService.findById(createdOwner.getId());

		assertTrue(foundOwner.isPresent());
		assertEquals(createdOwner.getId(), foundOwner.get().getId());
		assertEquals("Maria", foundOwner.get().getFirstName());
		assertEquals("Gomez", foundOwner.get().getLastName());

		log.info("Owner found: {}", foundOwner.get());
	}

	@Test
	public void testFindAllOwners() {
		Owner owner = new Owner();
		owner.setFirstName("Luis");
		owner.setLastName("Castro");
		owner.setAddress("Jr. Central 789");
		owner.setCity("Cusco");
		owner.setTelephone("923456789");

		ownerService.create(owner);

		assertFalse(ownerService.findAll().isEmpty());

		log.info("Owners list: {}", ownerService.findAll());
	}

	@Test
	public void testUpdateOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Rosa");
		owner.setLastName("Diaz");
		owner.setAddress("Av. Principal 111");
		owner.setCity("Trujillo");
		owner.setTelephone("934567890");

		Owner createdOwner = ownerService.create(owner);

		Owner updatedData = new Owner();
		updatedData.setFirstName("Rosa Actualizada");
		updatedData.setLastName("Diaz Actualizada");
		updatedData.setAddress("Av. Actualizada 222");
		updatedData.setCity("Lima");
		updatedData.setTelephone("945678901");

		Owner updatedOwner = ownerService.update(createdOwner.getId(), updatedData);

		assertNotNull(updatedOwner);
		assertEquals(createdOwner.getId(), updatedOwner.getId());
		assertEquals("Rosa Actualizada", updatedOwner.getFirstName());
		assertEquals("Diaz Actualizada", updatedOwner.getLastName());
		assertEquals("Av. Actualizada 222", updatedOwner.getAddress());
		assertEquals("Lima", updatedOwner.getCity());
		assertEquals("945678901", updatedOwner.getTelephone());

		log.info("Owner updated: {}", updatedOwner);
	}

	@Test
	public void testDeleteOwner() {
		Owner owner = new Owner();
		owner.setFirstName("Miguel");
		owner.setLastName("Rojas");
		owner.setAddress("Calle Norte 333");
		owner.setCity("Piura");
		owner.setTelephone("956789012");

		Owner createdOwner = ownerService.create(owner);
		Integer id = createdOwner.getId();

		ownerService.delete(id);

		Optional<Owner> deletedOwner = ownerService.findById(id);

		assertFalse(deletedOwner.isPresent());

		log.info("Owner deleted with id: {}", id);
	}
}