package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class VisitServiceTest {

	@Autowired
	private VisitService visitService;

	/**
	 * CASO 4 - Crear visita
	 */
	@Test
	@Order(1)
	public void testCreateVisit() {

		int PET_ID = 1;
		int VET_ID = 2;
		LocalDate VISIT_DATE = LocalDate.of(2024, 6, 10);
		String DESCRIPTION = "annual vaccination";
		BigDecimal COST = new BigDecimal("55.00");

		VisitDTO visitDTO = VisitDTO.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		VisitDTO newVisitDTO = this.visitService.create(visitDTO);

		log.info("VISIT CREATED: " + newVisitDTO);

		assertNotNull(newVisitDTO.getId());
		assertEquals(PET_ID, newVisitDTO.getPetId());
		assertEquals(VET_ID, newVisitDTO.getVetId());
		assertEquals(VISIT_DATE, newVisitDTO.getVisitDate());
		assertEquals(DESCRIPTION, newVisitDTO.getDescription());
		assertEquals(0, COST.compareTo(newVisitDTO.getCost()));
	}

	/**
	 * CASO 4 - Buscar visita por id
	 */
	@Test
	@Order(2)
	public void testFindVisitById() {

		final int PET_ID_EXPECTED = 7;
		final int VET_ID_EXPECTED = 2;
		final LocalDate VISIT_DATE_EXPECTED = LocalDate.of(2010, 3, 4);
		final String DESCRIPTION_EXPECTED = "rabies shot";
		final BigDecimal COST_EXPECTED = new BigDecimal("45.00");

		Integer ID = 1;

		VisitDTO visit = null;

		try {
			visit = this.visitService.findById(ID);
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}

		log.info("VISIT FOUND: " + visit);

		assertEquals(PET_ID_EXPECTED, visit.getPetId());
		assertEquals(VET_ID_EXPECTED, visit.getVetId());
		assertEquals(VISIT_DATE_EXPECTED, visit.getVisitDate());
		assertEquals(DESCRIPTION_EXPECTED, visit.getDescription());
		assertEquals(0, COST_EXPECTED.compareTo(visit.getCost()));
	}

	/**
	 * CASO 4 - Actualizar visita
	 */
	@Test
	@Order(3)
	public void testUpdateVisit() {

		int PET_ID = 3;
		int VET_ID = 4;
		LocalDate VISIT_DATE = LocalDate.of(2024, 3, 15);
		String DESCRIPTION = "general checkup";
		BigDecimal COST = new BigDecimal("70.00");

		int UP_PET_ID = 5;
		int UP_VET_ID = 3;
		LocalDate UP_VISIT_DATE = LocalDate.of(2024, 4, 20);
		String UP_DESCRIPTION = "follow-up checkup";
		BigDecimal UP_COST = new BigDecimal("85.50");

		VisitDTO visitDTO = VisitDTO.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		log.info(">" + visitDTO);
		VisitDTO visitCreated = this.visitService.create(visitDTO);
		log.info(">>" + visitCreated);

		visitCreated.setPetId(UP_PET_ID);
		visitCreated.setVetId(UP_VET_ID);
		visitCreated.setVisitDate(UP_VISIT_DATE);
		visitCreated.setDescription(UP_DESCRIPTION);
		visitCreated.setCost(UP_COST);

		VisitDTO updatedVisit = this.visitService.update(visitCreated);
		log.info(">>>>" + updatedVisit);

		assertEquals(UP_PET_ID, updatedVisit.getPetId());
		assertEquals(UP_VET_ID, updatedVisit.getVetId());
		assertEquals(UP_VISIT_DATE, updatedVisit.getVisitDate());
		assertEquals(UP_DESCRIPTION, updatedVisit.getDescription());
		assertEquals(0, UP_COST.compareTo(updatedVisit.getCost()));
	}

	/**
	 * CASO 4 - Eliminar visita
	 */
	@Test
	@Order(4)
	public void testDeleteVisit() {

		int PET_ID = 10;
		int VET_ID = 1;
		LocalDate VISIT_DATE = LocalDate.of(2024, 5, 1);
		String DESCRIPTION = "nail trimming";
		BigDecimal COST = new BigDecimal("25.00");

		VisitDTO visitDTO = VisitDTO.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		VisitDTO newVisitDTO = this.visitService.create(visitDTO);
		log.info("" + newVisitDTO);

		try {
			this.visitService.delete(newVisitDTO.getId());
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}

		try {
			this.visitService.findById(newVisitDTO.getId());
			assertTrue(false);
		} catch (VisitNotFoundException e) {
			assertTrue(true);
		}
	}
}
