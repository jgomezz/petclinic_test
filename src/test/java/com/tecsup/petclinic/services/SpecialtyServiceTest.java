package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyServiceImpl specialtyService;

    @Autowired(required = false)
    private SpecialtyService specialtyServiceReal; // Usa este si quieres probar el bean real de Spring Boot

    /**
     * Test con Mockito - Buscar por ID
     */
    @Test
    public void testFindSpecialityById() throws SpecialtyNotFoundException {
        Integer ID = 1;
        String NAME = "Cardiology";

        Specialty specialty = new Specialty();
        specialty.setId(ID);
        specialty.setName(NAME);

        when(specialtyRepository.findById(ID)).thenReturn(Optional.of(specialty));

        Specialty found = specialtyService.findById(ID);

        assertNotNull(found);
        assertEquals(NAME, found.getName());
        verify(specialtyRepository, times(1)).findById(ID);
    }

    /**
     * Test con Mockito - Eliminar especialidad
     */
    @Test
    public void testDeleteSpeciality() throws SpecialtyNotFoundException {
        Integer ID = 1;

        Specialty specialty = new Specialty();
        specialty.setId(ID);
        specialty.setName("Neurology");

        when(specialtyRepository.findById(ID)).thenReturn(Optional.of(specialty));

        specialtyService.delete(ID);

        verify(specialtyRepository, times(1)).delete(specialty);
    }

    /**
     * Test con Spring Boot real - Listar todas las especialidades
     */
    @Test
    public void testFindAllSpecialities() {
        if (specialtyServiceReal == null) {
            log.warn("Spring context not loaded, se omite testFindAllSpecialities");
            return;
        }

        List<SpecialtyDTO> all = specialtyServiceReal.findAll();
        assertNotNull(all, "La lista de especialidades no debe ser null");
        log.info("Total specialities = {}", all.size());
    }

    /**
     * Test con Spring Boot real - Buscar por descripción
     */
    @Test
    public void testFindSpecialityByDescription() {
        if (specialtyServiceReal == null) {
            log.warn("Spring context not loaded, se omite testFindSpecialityByDescription");
            return;
        }

        String desc = "Radiology";
        SpecialtyDTO dto = new SpecialtyDTO();
        dto.setDescription(desc);

        specialtyServiceReal.create(dto);

        List<SpecialtyDTO> found = specialtyServiceReal.findByDescription(desc);
        assertNotNull(found, "El resultado de la búsqueda no debe ser null");
        assertTrue(found.stream().anyMatch(s -> desc.equals(s.getDescription())),
                "Debe encontrarse al menos una especialidad con la descripción: " + desc);
    }
}
