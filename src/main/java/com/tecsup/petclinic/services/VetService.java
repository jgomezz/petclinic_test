package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;

import java.util.List;
import java.util.Optional;

public interface VetService {

    Vet create(Vet vet);

    Vet update(Integer id, Vet vet);

    Optional<Vet> findById(Integer id);

    List<Vet> findAll();

    void delete(Integer id);
}