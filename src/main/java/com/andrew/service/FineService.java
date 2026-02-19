package com.andrew.service;

import java.math.BigDecimal;

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
}
