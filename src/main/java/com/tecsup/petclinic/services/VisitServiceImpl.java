package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        Visit visit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        VisitDTO visit = findById(id);
        visitRepository.delete(visitMapper.mapToEntity(visit));
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        return visitRepository.findById(id)
                .map(visitMapper::mapToDto)
                .orElseThrow(() -> new VisitNotFoundException("Visit record not found...!"));
    }

    @Override
    public List<VisitDTO> findByPetId(Integer petId) {
        return toDTOs(visitRepository.findByPetId(petId));
    }

    @Override
    public List<VisitDTO> findByVetId(Integer vetId) {
        return toDTOs(visitRepository.findByVetId(vetId));
    }

    @Override
    public List<VisitDTO> findByDateBetween(LocalDate start, LocalDate end) {
        return toDTOs(visitRepository.findByVisitDateBetween(start, end));
    }

    @Override
    public List<VisitDTO> findByVetIdAndDateBetween(Integer vetId, LocalDate start, LocalDate end) {
        return toDTOs(visitRepository.findByVetIdAndVisitDateBetween(vetId, start, end));
    }

    @Override
    public long countVisitsByPet(Integer petId) {
        return visitRepository.countByPetId(petId);
    }

    @Override
    public BigDecimal calculateTotalCostByPet(Integer petId) {
        return visitRepository.findByPetId(petId).stream()
                .map(Visit::getCost)
                .filter(cost -> cost != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<VisitDTO> findAll() {
        return toDTOs(visitRepository.findAll());
    }

    private List<VisitDTO> toDTOs(List<Visit> visits) {
        visits.forEach(visit -> log.info("{}", visit));
        return visits.stream().map(visitMapper::mapToDto).collect(Collectors.toList());
    }
}
