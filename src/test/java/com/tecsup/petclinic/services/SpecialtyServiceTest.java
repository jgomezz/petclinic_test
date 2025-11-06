package com.tecsup.petclinic.services;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyServiceImpl specialtyService;

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
}
