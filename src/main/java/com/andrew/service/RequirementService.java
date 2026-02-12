package com.andrew.service;

import com.andrew.dto.requirement.RequirementCreateDTO;
import com.andrew.dto.requirement.RequirementResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.RequirementMapper;
import com.andrew.model.Requirement;
import com.andrew.repository.RequirementRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RequirementService {
    @Inject
    RequirementRepository requirementRepository;

    @Inject
    RequirementMapper requirementMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public RequirementResponseDTO createRequirement(RequirementCreateDTO dto) {
        return createRequirement(requirementMapper.toEntity(dto));
    }

    @Transactional
    private RequirementResponseDTO createRequirement(Requirement requirement) {
        requirementRepository.save(requirement);
        return requirementMapper.toResponse(requirement);
    }

    @Transactional
    public RequirementResponseDTO updateRequirement(Long id, RequirementCreateDTO dto) {
        requirementRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Requirement", id));

        Requirement toUpdate = requirementMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return requirementMapper.toResponse(requirementRepository.update(toUpdate));
    }

    @Transactional
    public void deleteRequirement(Long id) {
        Requirement requirement = requirementRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Requirement", id));

        requirementRepository.delete(requirement);
    }
}
