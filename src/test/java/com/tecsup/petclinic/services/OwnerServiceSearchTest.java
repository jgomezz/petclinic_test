package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceSearchTest {

	@Mock
	private OwnerRepository ownerRepository;

	@InjectMocks
	private OwnerServiceImpl ownerService;

	@Test
	public void testFindOwnerById() throws Exception {

		Owner ownerExpected = new Owner(
				1,
				"George",
				"Franklin",
				"110 W. Liberty St.",
				"Madison",
				"6085551023");

		when(ownerRepository.findById(1)).thenReturn(Optional.of(ownerExpected));

		Owner owner = ownerService.findById(1);

		assertNotNull(owner);
		assertEquals(1, owner.getId());
		assertEquals("George", owner.getFirstName());
		assertEquals("Franklin", owner.getLastName());
		assertEquals("Madison", owner.getCity());
		verify(ownerRepository).findById(1);
	}

	@Test
	public void testFindByLastName() {

		Owner ownerExpected = new Owner(
				1,
				"George",
				"Franklin",
				"110 W. Liberty St.",
				"Madison",
				"6085551023");

		when(ownerRepository.findByLastName("Franklin")).thenReturn(List.of(ownerExpected));

		List<Owner> owners = ownerService.findByLastName("Franklin");

		assertNotNull(owners);
		assertEquals(1, owners.size());
		assertEquals("Franklin", owners.get(0).getLastName());
		verify(ownerRepository).findByLastName("Franklin");
	}

	@Test
	public void testFindByCity() {

		Owner ownerExpected = new Owner(
				1,
				"George",
				"Franklin",
				"110 W. Liberty St.",
				"Madison",
				"6085551023");

		when(ownerRepository.findByCity("Madison")).thenReturn(List.of(ownerExpected));

		List<Owner> owners = ownerService.findByCity("Madison");

		assertNotNull(owners);
		assertEquals(1, owners.size());
		assertEquals("Madison", owners.get(0).getCity());
		verify(ownerRepository).findByCity("Madison");
	}
}
