package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.exceptions.SpecialtyNotAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VetSpecialtyService {

    VetSpecialtyDTO assignSpecialtyToVet(VetSpecialtyDTO vetSpecialtyDTO)
            throws DuplicateVetSpecialtyException;

    void removeSpecialtyFromVet(Integer vetId, Integer specialtyId)
            throws VetSpecialtyNotFoundException;

    List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId);

    List<VetDTO> findVetsBySpecialty(Integer specialtyId);

    VetSpecialtyDTO setPrimarySpecialty(Integer vetId, Integer specialtyId)
            throws SpecialtyNotAssignedException;

    List<VetDTO> findVetsByMinExperience(Integer specialtyId, Integer minYears);

    VetSpecialtyDTO updateYearsExperience(Integer vetId, Integer specialtyId, Integer years)
            throws VetSpecialtyNotFoundException;

    VetSpecialtyDTO updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate date)
            throws VetSpecialtyNotFoundException;

    List<VetSpecialtyDTO> findAll();
}
