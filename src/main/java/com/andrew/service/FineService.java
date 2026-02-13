package com.andrew.service;

import java.math.BigDecimal;

import com.andrew.dto.fine.FineCreateDTO;
import com.andrew.dto.fine.FineResponseDTO;
import com.andrew.mapper.dto.FineMapper;
import com.andrew.model.Case;
import com.andrew.model.Fine;
import com.andrew.repository.FineRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FineService {
    @Inject
    FineRepository fineRepository;

    @Inject
    FineMapper fineMapper;

    @Transactional
    public FineResponseDTO createFine(Case c, BigDecimal amount) {
        Fine fine = new Fine(c, amount);
        fineRepository.save(fine);
        return fineMapper.toResponse(fine);
    }

    //TODO: do we need this?
    // @Transactional
    // public FineResponseDTO updateFine(Long id, FineCreateDTO dto) {
    //     fineRepository.findById(id)
    //         .orElseThrow(() -> new NotFoundException("Fine", id));

    //     Fine toUpdate = fineMapper.toEntity(dto);
    //     toUpdate.setId(id);

    //     return fineMapper.toResponse(fineRepository.update(toUpdate));
    // }

    // @Transactional
    // public void deleteFine(Long id) {
    //     Fine fine = fineRepository.findById(id)
    //         .orElseThrow(() -> new NotFoundException("Fine", id));

    //     fineRepository.delete(fine);
    // }
}
