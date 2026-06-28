package com.tecsup.petclinic.services;


import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

public interface SpecialtyService {

    Specialty findById(Integer id) throws SpecialtyNotFoundException;

    List<Specialty> findAll();

    Specialty create(Specialty specialty);

    Specialty update(Specialty specialty);

    void delete(Integer id) throws SpecialtyNotFoundException;
}
