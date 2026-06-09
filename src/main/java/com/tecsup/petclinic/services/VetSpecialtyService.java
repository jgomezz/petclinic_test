package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;

import java.util.List;

public interface VetSpecialtyService {

    VetSpecialty create(VetSpecialty vetSpecialty);

    VetSpecialty findById(VetSpecialtyId id) throws Exception;

    List<VetSpecialty> findByVetId(int vetId);

    List<VetSpecialty> findBySpecialtyId(int specialtyId);

    void delete(VetSpecialtyId id);
}