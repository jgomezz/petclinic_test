package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {

    Owner create(Owner owner);

    Owner update(Integer id, Owner owner);

    Optional<Owner> findById(Integer id);

    List<Owner> findAll();

    void delete(Integer id);
}