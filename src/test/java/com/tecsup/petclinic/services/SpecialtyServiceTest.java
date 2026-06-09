package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SpecialtyServiceTest {

	@Autowired
	private SpecialtyService specialtyService;

	/**
	 * CASO 3 - Crear especialidad
	 */
	@Test
	@Order(1)
	public void testCreateSpecialty() {

		String NAME = "cardiology";
		String OFFICE = "Central";
		int H_OPEN = 7;
		int H_CLOSE = 15;

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);

		log.info("SPECIALTY CREATED: " + newSpecialtyDTO);

		assertNotNull(newSpecialtyDTO.getId());
		assertEquals(NAME, newSpecialtyDTO.getName());
		assertEquals(OFFICE, newSpecialtyDTO.getOffice());
		assertEquals(H_OPEN, newSpecialtyDTO.getHOpen());
		assertEquals(H_CLOSE, newSpecialtyDTO.getHClose());
	}

	/**
	 * CASO 3 - Buscar especialidad por id
	 */
	@Test
	@Order(2)
	public void testFindSpecialtyById() {

		final String NAME_EXPECTED = "radiology";
		final String OFFICE_EXPECTED = "Farewell";
		final int H_OPEN_EXPECTED = 8;
		final int H_CLOSE_EXPECTED = 18;

		Integer ID = 1;

		SpecialtyDTO specialty = null;

		try {
			specialty = this.specialtyService.findById(ID);
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}

		log.info("SPECIALTY FOUND: " + specialty);

		assertEquals(NAME_EXPECTED, specialty.getName());
		assertEquals(OFFICE_EXPECTED, specialty.getOffice());
		assertEquals(H_OPEN_EXPECTED, specialty.getHOpen());
		assertEquals(H_CLOSE_EXPECTED, specialty.getHClose());
	}

	/**
	 * CASO 3 - Actualizar especialidad
	 */
	@Test
	@Order(3)
	public void testUpdateSpecialty() {

		String NAME = "dermatology";
		String OFFICE = "North Wing";
		int H_OPEN = 9;
		int H_CLOSE = 17;

		String UP_NAME = "dermatology-advanced";
		String UP_OFFICE = "South Wing";
		int UP_H_OPEN = 10;
		int UP_H_CLOSE = 20;

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		log.info(">" + specialtyDTO);
		SpecialtyDTO specialtyCreated = this.specialtyService.create(specialtyDTO);
		log.info(">>" + specialtyCreated);

		specialtyCreated.setName(UP_NAME);
		specialtyCreated.setOffice(UP_OFFICE);
		specialtyCreated.setHOpen(UP_H_OPEN);
		specialtyCreated.setHClose(UP_H_CLOSE);

		SpecialtyDTO updatedSpecialty = this.specialtyService.update(specialtyCreated);
		log.info(">>>>" + updatedSpecialty);

		assertEquals(UP_NAME, updatedSpecialty.getName());
		assertEquals(UP_OFFICE, updatedSpecialty.getOffice());
		assertEquals(UP_H_OPEN, updatedSpecialty.getHOpen());
		assertEquals(UP_H_CLOSE, updatedSpecialty.getHClose());
	}

	/**
	 * CASO 3 - Eliminar especialidad
	 */
	@Test
	@Order(4)
	public void testDeleteSpecialty() {

		String NAME = "nutrition";
		String OFFICE = "Wellness";
		int H_OPEN = 8;
		int H_CLOSE = 16;

		SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
				.name(NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);
		log.info("" + newSpecialtyDTO);

		try {
			this.specialtyService.delete(newSpecialtyDTO.getId());
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}

		try {
			this.specialtyService.findById(newSpecialtyDTO.getId());
			assertTrue(false);
		} catch (SpecialtyNotFoundException e) {
			assertTrue(true);
		}
	}
}
