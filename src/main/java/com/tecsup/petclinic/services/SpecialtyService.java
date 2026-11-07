package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.InvalidHourRangeException;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

public interface SpecialtyService {

    SpecialtyDTO create(SpecialtyDTO specialtyDTO)
            throws InvalidHourRangeException, InvalidScheduleException;

    SpecialtyDTO update(SpecialtyDTO specialtyDTO)
            throws InvalidHourRangeException, InvalidScheduleException;

    void delete(Integer id) throws SpecialtyNotFoundException;

    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

    List<SpecialtyDTO> findByName(String name);

    List<SpecialtyDTO> findByOffice(String office);

    List<SpecialtyDTO> findAll();
}
