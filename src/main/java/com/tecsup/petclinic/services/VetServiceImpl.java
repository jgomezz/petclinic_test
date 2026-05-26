package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = vetMapper.mapToEntity(vetDTO);
        Vet vetSaved = vetRepository.save(vet);
        return vetMapper.mapToDTO(vetSaved);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) throws VetNotFoundException {
        Vet vet = vetRepository.findById(vetDTO.getId())
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + vetDTO.getId()));

        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        vet.setEmail(vetDTO.getEmail());
        vet.setPhone(vetDTO.getPhone());
        vet.setActive(vetDTO.getActive());

        Vet vetUpdated = vetRepository.save(vet);
        return vetMapper.mapToDTO(vetUpdated);
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));

        return vetMapper.mapToDTO(vet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));

        vetRepository.delete(vet);
    }
}