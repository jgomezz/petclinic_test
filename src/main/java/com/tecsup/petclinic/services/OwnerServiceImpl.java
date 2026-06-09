package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Owner update(Integer id, Owner owner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        existingOwner.setFirstName(owner.getFirstName());
        existingOwner.setLastName(owner.getLastName());
        existingOwner.setAddress(owner.getAddress());
        existingOwner.setCity(owner.getCity());
        existingOwner.setTelephone(owner.getTelephone());

        return ownerRepository.save(existingOwner);
    }

    @Override
    public Optional<Owner> findById(Integer id) {
        return ownerRepository.findById(id);
    }

    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        ownerRepository.deleteById(id);
    }
}