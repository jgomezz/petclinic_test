package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    // -------------------------------------------------------
    // TEST 1: Asignar especialidad a veterinario
    // vet 1 (James Carter) no tiene ninguna → podemos asignarle
    // -------------------------------------------------------
    @Test
    public void testAddVetSpecialty() {

        int VET_ID       = 1;  // James Carter — sin especialidad
        int SPECIALTY_ID = 3;  // dentistry

        VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setVetId(VET_ID);
        vetSpecialty.setSpecialtyId(SPECIALTY_ID);
        vetSpecialty.setCertificationDate(LocalDate.of(2020, 1, 15));
        vetSpecialty.setYearsExperience(4);
        vetSpecialty.setIsPrimary(true);
        vetSpecialty.setNotes("Test assignment");

        VetSpecialty saved = vetSpecialtyService.create(vetSpecialty);
        log.info("VetSpecialty creada: " + saved);

        assertNotNull(saved);
        assertEquals(VET_ID,       saved.getVetId());
        assertEquals(SPECIALTY_ID, saved.getSpecialtyId());

        // Limpiar
        vetSpecialtyService.delete(new VetSpecialtyId(VET_ID, SPECIALTY_ID));
    }

    // -------------------------------------------------------
    // TEST 2: Buscar especialidades por veterinario
    // vet 3 (Linda Douglas) tiene 2 especialidades: surgery + dentistry
    // -------------------------------------------------------
    @Test
    public void testFindSpecialtiesByVetId() {

        int VET_ID                    = 3;  // Linda Douglas
        int EXPECTED_SPECIALTIES_SIZE = 2;  // surgery + dentistry

        List<VetSpecialty> result = vetSpecialtyService.findByVetId(VET_ID);
        log.info("Especialidades del vet " + VET_ID + ": " + result);

        assertNotNull(result);
        assertEquals(EXPECTED_SPECIALTIES_SIZE, result.size());
    }

    // -------------------------------------------------------
    // TEST 3: Buscar veterinarios por especialidad
    // specialty 1 (radiology) → vet 2 (Helen) + vet 5 (Henry) = 2 vets
    // -------------------------------------------------------
    @Test
    public void testFindVetsBySpecialtyId() {

        int SPECIALTY_ID       = 1;  // radiology
        int EXPECTED_VETS_SIZE = 2;  // Helen Leary + Henry Stevens

        List<VetSpecialty> result = vetSpecialtyService.findBySpecialtyId(SPECIALTY_ID);
        log.info("Vets con specialty " + SPECIALTY_ID + ": " + result);

        assertNotNull(result);
        assertEquals(EXPECTED_VETS_SIZE, result.size());
    }

    // -------------------------------------------------------
    // TEST 4: Eliminar relación veterinario-especialidad
    // Crear una nueva relación y luego eliminarla
    // -------------------------------------------------------
    @Test
    public void testDeleteVetSpecialty() {

        int VET_ID       = 6;  // Sharon Jenkins — sin especialidad
        int SPECIALTY_ID = 2;  // surgery

        // Crear relación temporal
        VetSpecialty vetSpecialty = new VetSpecialty();
        vetSpecialty.setVetId(VET_ID);
        vetSpecialty.setSpecialtyId(SPECIALTY_ID);
        vetSpecialty.setCertificationDate(LocalDate.of(2021, 5, 10));
        vetSpecialty.setYearsExperience(2);
        vetSpecialty.setIsPrimary(false);
        vetSpecialty.setNotes("Temporal para test de eliminación");

        VetSpecialty saved = vetSpecialtyService.create(vetSpecialty);
        log.info("VetSpecialty creada para eliminar: " + saved);
        assertNotNull(saved);

        // Eliminar
        VetSpecialtyId id = new VetSpecialtyId(VET_ID, SPECIALTY_ID);
        vetSpecialtyService.delete(id);
        log.info("VetSpecialty eliminada: vet=" + VET_ID + ", specialty=" + SPECIALTY_ID);

        // Verificar que ya no existe
        List<VetSpecialty> result = vetSpecialtyService.findByVetId(VET_ID);
        boolean stillExists = result.stream()
                .anyMatch(vs -> vs.getSpecialtyId() == SPECIALTY_ID);

        assertFalse(stillExists, "La relación debería haber sido eliminada");
    }
}