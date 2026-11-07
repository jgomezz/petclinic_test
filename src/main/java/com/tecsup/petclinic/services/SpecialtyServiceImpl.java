package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.InvalidHourRangeException;
import com.tecsup.petclinic.exceptions.InvalidScheduleException;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO)
            throws InvalidHourRangeException, InvalidScheduleException {
        validateHours(specialtyDTO);
        Specialty specialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO)
            throws InvalidHourRangeException, InvalidScheduleException {
        validateHours(specialtyDTO);
        Specialty specialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        SpecialtyDTO specialty = findById(id);
        specialtyRepository.delete(specialtyMapper.mapToEntity(specialty));
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        return specialtyRepository.findById(id)
                .map(specialtyMapper::mapToDto)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty record not found...!"));
    }

    @Override
    public List<SpecialtyDTO> findByName(String name) {
        List<Specialty> specialties = specialtyRepository.findByName(name);
        specialties.forEach(specialty -> log.info("{}", specialty));
        return specialties.stream().map(specialtyMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<SpecialtyDTO> findByOffice(String office) {
        List<Specialty> specialties = specialtyRepository.findByOffice(office);
        specialties.forEach(specialty -> log.info("{}", specialty));
        return specialties.stream().map(specialtyMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll().stream()
                .map(specialtyMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private void validateHours(SpecialtyDTO specialtyDTO)
            throws InvalidHourRangeException, InvalidScheduleException {
        Integer hOpen = specialtyDTO.getOpenHour();
        Integer hClose = specialtyDTO.getCloseHour();

        if (hOpen == null || hClose == null) {
            throw new InvalidHourRangeException("Opening and closing hours are required...!");
        }
        if (hOpen < MIN_HOUR || hOpen > MAX_HOUR || hClose < MIN_HOUR || hClose > MAX_HOUR) {
            throw new InvalidHourRangeException("Hours must be between 0 and 23...!");
        }
        if (hOpen >= hClose) {
            throw new InvalidScheduleException("Opening hour must be lower than closing hour...!");
        }
    }
}
