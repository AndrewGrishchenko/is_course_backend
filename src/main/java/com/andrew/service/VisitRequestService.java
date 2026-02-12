package com.andrew.service;

import com.andrew.dto.VisitRequest.VisitRequestCreateDTO;
import com.andrew.dto.VisitRequest.VisitRequestResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.VisitRequestMapper;
import com.andrew.model.VisitRequest;
import com.andrew.repository.VisitRequestRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class VisitRequestService {
    @Inject
    VisitRequestRepository visitRequestRepository;

    @Inject
    VisitRequestMapper visitRequestMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public VisitRequestResponseDTO createVisitRequest(VisitRequestCreateDTO dto) {
        return createVisitRequest(visitRequestMapper.toEntity(dto));
    }

    @Transactional
    private VisitRequestResponseDTO createVisitRequest(VisitRequest visitRequest) {
        visitRequestRepository.save(visitRequest);
        return visitRequestMapper.toResponse(visitRequest);
    }

    @Transactional
    public VisitRequestResponseDTO updateVisitRequest(Long id, VisitRequestCreateDTO dto) {
        visitRequestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("VisitRequest", id));
        
        VisitRequest toUpdate = visitRequestMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return visitRequestMapper.toResponse(visitRequestRepository.update(toUpdate));
    }

    @Transactional
    public void deleteVisitRequest(Long id) {
        VisitRequest visitRequest = visitRequestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("VisitRequest", id));
        
        visitRequestRepository.delete(visitRequest);
    }
}
