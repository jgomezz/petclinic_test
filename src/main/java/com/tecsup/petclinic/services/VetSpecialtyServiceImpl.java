package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    @Autowired
    private VetSpecialtyRepository vetSpecialtyRepository;

    @Override
    public VetSpecialty create(VetSpecialty vetSpecialty) {
        return vetSpecialtyRepository.save(vetSpecialty);
    }

    @Override
    public VetSpecialty findById(VetSpecialtyId id) throws Exception {
        Optional<VetSpecialty> vetSpecialty = vetSpecialtyRepository.findById(id);
        if (!vetSpecialty.isPresent()) {
            throw new Exception("VetSpecialty no encontrada: " + id);
        }
        return vetSpecialty.get();
    }

    @Override
    public List<VetSpecialty> findByVetId(int vetId) {
        return vetSpecialtyRepository.findByVetId(vetId);
    }

    @Override
    public List<VetSpecialty> findBySpecialtyId(int specialtyId) {
        return vetSpecialtyRepository.findBySpecialtyId(specialtyId);
    }

    @Override
    public void delete(VetSpecialtyId id) {
        vetSpecialtyRepository.deleteById(id);
    }
}