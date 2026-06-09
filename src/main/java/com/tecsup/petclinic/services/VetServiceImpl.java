package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet create(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public Vet update(Integer id, Vet vet) {
        Vet existingVet = vetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vet not found"));

        existingVet.setFirstName(vet.getFirstName());
        existingVet.setLastName(vet.getLastName());

        return vetRepository.save(existingVet);
    }

    @Override
    public Optional<Vet> findById(Integer id) {
        return vetRepository.findById(id);
    }

    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        vetRepository.deleteById(id);
    }
}