package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

public interface OwnerService {

    OwnerDTO create(OwnerDTO ownerDTO);

    OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException;

    void delete(Integer id) throws OwnerNotFoundException;

    OwnerDTO findById(Integer id) throws OwnerNotFoundException;
}