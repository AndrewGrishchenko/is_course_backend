package com.andrew.service;

import com.andrew.dto.fine.FineCreateDTO;
import com.andrew.dto.fine.FineResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.FineMapper;
import com.andrew.model.Fine;
import com.andrew.repository.FineRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FineService {
    @Inject
    FineRepository fineRepository;

    @Inject
    FineMapper fineMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public FineResponseDTO createFine(FineCreateDTO dto) {
        return createFine(fineMapper.toEntity(dto));
    }

    @Transactional
    private FineResponseDTO createFine(Fine fine) {
        fineRepository.save(fine);
        return fineMapper.toResponse(fine);
    }

    @Transactional
    public FineResponseDTO updateFine(Long id, FineCreateDTO dto) {
        fineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Fine", id));

        Fine toUpdate = fineMapper.toEntity(dto);
        toUpdate.setId(id);

        return fineMapper.toResponse(fineRepository.update(toUpdate));
    }

    @Transactional
    public void deleteFine(Long id) {
        Fine fine = fineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Fine", id));

        fineRepository.delete(fine);
    }
}
