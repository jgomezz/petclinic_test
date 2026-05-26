package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	@Test
	public void testCreateOwner() {
		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName("Calep")
				.lastName("Neyra")
				.address("Av. Principal 123")
				.city("Lima")
				.telephone("999888777")
				.build();

		OwnerDTO ownerCreated = ownerService.create(ownerDTO);

		log.info("Owner created: {}", ownerCreated);

		assertNotNull(ownerCreated);
		assertNotNull(ownerCreated.getId());
		assertEquals("Calep", ownerCreated.getFirstName());
		assertEquals("Neyra", ownerCreated.getLastName());
		assertEquals("Lima", ownerCreated.getCity());
		assertEquals("999888777", ownerCreated.getTelephone());
	}

	@Test
	public void testUpdateOwner() {
		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName("Luis")
				.lastName("Ramirez")
				.address("Calle Antigua 111")
				.city("Lima")
				.telephone("111222333")
				.build();

		OwnerDTO ownerCreated = ownerService.create(ownerDTO);

		ownerCreated.setFirstName("Luis Actualizado");
		ownerCreated.setLastName("Ramirez Actualizado");
		ownerCreated.setAddress("Calle Nueva 222");
		ownerCreated.setCity("Arequipa");
		ownerCreated.setTelephone("444555666");

		OwnerDTO ownerUpdated = ownerService.update(ownerCreated);

		log.info("Owner updated: {}", ownerUpdated);

		assertNotNull(ownerUpdated);
		assertEquals(ownerCreated.getId(), ownerUpdated.getId());
		assertEquals("Luis Actualizado", ownerUpdated.getFirstName());
		assertEquals("Ramirez Actualizado", ownerUpdated.getLastName());
		assertEquals("Calle Nueva 222", ownerUpdated.getAddress());
		assertEquals("Arequipa", ownerUpdated.getCity());
		assertEquals("444555666", ownerUpdated.getTelephone());
	}

	@Test
	public void testDeleteOwner() {
		OwnerDTO ownerDTO = OwnerDTO.builder()
				.firstName("Pedro")
				.lastName("Lopez")
				.address("Av. Delete 555")
				.city("Cusco")
				.telephone("777888999")
				.build();

		OwnerDTO ownerCreated = ownerService.create(ownerDTO);

		assertNotNull(ownerCreated.getId());

		try {
			ownerService.delete(ownerCreated.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		assertThrows(OwnerNotFoundException.class, () -> {
			ownerService.findById(ownerCreated.getId());
		});
	}
}