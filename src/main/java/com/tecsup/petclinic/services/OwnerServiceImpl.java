package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner owner = toEntity(ownerDTO);
        Owner ownerSaved = ownerRepository.save(owner);
        return toDTO(ownerSaved);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerDTO.getId())
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + ownerDTO.getId()));

        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());

        Owner ownerUpdated = ownerRepository.save(owner);
        return toDTO(ownerUpdated);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));

        ownerRepository.delete(owner);
    }

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));

        return toDTO(owner);
    }

    private OwnerDTO toDTO(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .build();
    }

    private Owner toEntity(OwnerDTO ownerDTO) {
        return Owner.builder()
                .id(ownerDTO.getId())
                .firstName(ownerDTO.getFirstName())
                .lastName(ownerDTO.getLastName())
                .address(ownerDTO.getAddress())
                .city(ownerDTO.getCity())
                .telephone(ownerDTO.getTelephone())
                .build();
    }
}