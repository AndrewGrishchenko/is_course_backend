package com.andrew.service;

import com.andrew.dto.supply.SupplyCreateDTO;
import com.andrew.dto.supply.SupplyResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.SupplyMapper;
import com.andrew.model.Supply;
import com.andrew.repository.SupplyRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SupplyService {
    @Inject
    SupplyRepository supplyRepository;

    @Inject
    SupplyMapper supplyMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public SupplyResponseDTO createSupply(SupplyCreateDTO dto) {
        return createSupply(supplyMapper.toEntity(dto));
    }

    @Transactional
    private SupplyResponseDTO createSupply(Supply supply) {
        supplyRepository.save(supply);
        return supplyMapper.toResponse(supply);
    }

    @Transactional
    public SupplyResponseDTO updateSupply(Long id, SupplyCreateDTO dto) {
        supplyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Supply", id));

        Supply toUpdate = supplyMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return supplyMapper.toResponse(supplyRepository.update(toUpdate));
    }

    @Transactional
    public void deleteSupply(Long id) {
        Supply supply = supplyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Supply", id));
        
        supplyRepository.delete(supply);
    }
}
