package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vetRepository.delete(vetMapper.mapToEntity(vet));
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        return vetRepository.findById(id)
                .map(vetMapper::mapToDto)
                .orElseThrow(() -> new VetNotFoundException("Vet record not found...!"));
    }

    @Override
    public List<VetDTO> findByLastName(String lastName) {
        List<Vet> vets = vetRepository.findByLastName(lastName);
        vets.forEach(vet -> log.info("{}", vet));
        return vets.stream().map(vetMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findActiveVets() {
        return vetRepository.findByActiveTrue().stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findInactiveVets() {
        return vetRepository.findByActiveFalse().stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VetDTO deactivate(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vet.setActive(false);
        return update(vet);
    }

    @Override
    public VetDTO reactivate(Integer id) throws VetNotFoundException {
        VetDTO vet = findById(id);
        vet.setActive(true);
        return update(vet);
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll().stream()
                .map(vetMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
