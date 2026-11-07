package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.exceptions.SpecialtyNotAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetMapper vetMapper;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public VetSpecialtyDTO assignSpecialtyToVet(VetSpecialtyDTO dto)
            throws DuplicateVetSpecialtyException {

        VetSpecialtyId id = new VetSpecialtyId(dto.getVetId(), dto.getSpecialtyId());
        if (vetSpecialtyRepository.existsById(id)) {
            throw new DuplicateVetSpecialtyException(
                    "The specialty is already assigned to this vet...!");
        }
        VetSpecialty saved = vetSpecialtyRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public void removeSpecialtyFromVet(Integer vetId, Integer specialtyId)
            throws VetSpecialtyNotFoundException {
        VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);
        if (!vetSpecialtyRepository.existsById(id)) {
            throw new VetSpecialtyNotFoundException("Vet-specialty assignment not found...!");
        }
        vetSpecialtyRepository.deleteById(id);
    }

    @Override
    public List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
        return vetSpecialtyRepository.findByIdVetId(vetId).stream()
                .map(assignment -> specialtyRepository.findById(assignment.getId().getSpecialtyId()))
                .filter(java.util.Optional::isPresent)
                .map(optional -> specialtyMapper.mapToDto(optional.get()))
                .collect(Collectors.toList());
    }

    @Override
    public List<VetDTO> findVetsBySpecialty(Integer specialtyId) {
        return vetSpecialtyRepository.findByIdSpecialtyId(specialtyId).stream()
                .map(assignment -> vetRepository.findById(assignment.getId().getVetId()))
                .filter(java.util.Optional::isPresent)
                .map(optional -> vetMapper.mapToDto(optional.get()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VetSpecialtyDTO setPrimarySpecialty(Integer vetId, Integer specialtyId)
            throws SpecialtyNotAssignedException {

        VetSpecialtyId targetId = new VetSpecialtyId(vetId, specialtyId);
        VetSpecialty target = vetSpecialtyRepository.findById(targetId)
                .orElseThrow(() -> new SpecialtyNotAssignedException(
                        "The specialty is not assigned to this vet...!"));

        List<VetSpecialty> assignments = vetSpecialtyRepository.findByIdVetId(vetId);
        for (VetSpecialty assignment : assignments) {
            assignment.setIsPrimary(false);
        }
        target.setIsPrimary(true);

        vetSpecialtyRepository.saveAll(assignments);
        return toDTO(vetSpecialtyRepository.save(target));
    }

    @Override
    public List<VetDTO> findVetsByMinExperience(Integer specialtyId, Integer minYears) {
        return vetSpecialtyRepository
                .findByIdSpecialtyIdAndYearsExperienceGreaterThanEqual(specialtyId, minYears)
                .stream()
                .map(assignment -> vetRepository.findById(assignment.getId().getVetId()))
                .filter(java.util.Optional::isPresent)
                .map(optional -> vetMapper.mapToDto(optional.get()))
                .collect(Collectors.toList());
    }

    @Override
    public VetSpecialtyDTO updateYearsExperience(Integer vetId, Integer specialtyId, Integer years)
            throws VetSpecialtyNotFoundException {
        VetSpecialty assignment = findAssignment(vetId, specialtyId);
        assignment.setYearsExperience(years);
        return toDTO(vetSpecialtyRepository.save(assignment));
    }

    @Override
    public VetSpecialtyDTO updateCertificationDate(Integer vetId, Integer specialtyId, LocalDate date)
            throws VetSpecialtyNotFoundException {
        VetSpecialty assignment = findAssignment(vetId, specialtyId);
        assignment.setCertificationDate(date);
        return toDTO(vetSpecialtyRepository.save(assignment));
    }

    @Override
    public List<VetSpecialtyDTO> findAll() {
        return vetSpecialtyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private VetSpecialty findAssignment(Integer vetId, Integer specialtyId)
            throws VetSpecialtyNotFoundException {
        return vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId))
                .orElseThrow(() -> new VetSpecialtyNotFoundException(
                        "Vet-specialty assignment not found...!"));
    }

    private VetSpecialty toEntity(VetSpecialtyDTO dto) {
        VetSpecialty entity = new VetSpecialty();
        entity.setId(new VetSpecialtyId(dto.getVetId(), dto.getSpecialtyId()));
        entity.setCertificationDate(dto.getCertificationDate());
        entity.setYearsExperience(dto.getYearsExperience() == null ? 0 : dto.getYearsExperience());
        entity.setIsPrimary(dto.getIsPrimary() != null && dto.getIsPrimary());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    private VetSpecialtyDTO toDTO(VetSpecialty entity) {
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(entity.getId().getVetId());
        dto.setSpecialtyId(entity.getId().getSpecialtyId());
        dto.setCertificationDate(entity.getCertificationDate());
        dto.setYearsExperience(entity.getYearsExperience());
        dto.setIsPrimary(entity.getIsPrimary());
        dto.setNotes(entity.getNotes());
        return dto;
    }
}
