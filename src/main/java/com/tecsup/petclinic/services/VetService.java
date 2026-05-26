package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

public interface VetService {

    VetDTO create(VetDTO vetDTO);

    VetDTO update(VetDTO vetDTO) throws VetNotFoundException;

    VetDTO findById(Integer id) throws VetNotFoundException;

    void delete(Integer id) throws VetNotFoundException;
}