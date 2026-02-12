package com.andrew.service;

import com.andrew.dto.RequirementCondition.RequirementConditionCreateDTO;
import com.andrew.dto.RequirementCondition.RequirementConditionResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.RequirementConditionMapper;
import com.andrew.model.RequirementCondition;
import com.andrew.repository.RequirementConditionRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RequirementConditionService {
    @Inject
    RequirementConditionRepository requirementConditionRepository;

    @Inject
    RequirementConditionMapper requirementConditionMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public RequirementConditionResponseDTO createRequirementCondition(RequirementConditionCreateDTO dto) {
        return createRequirementCondition(requirementConditionMapper.toEntity(dto));
    }

    @Transactional
    public RequirementConditionResponseDTO createRequirementCondition(RequirementCondition requirementCondition) {
        requirementConditionRepository.save(requirementCondition);
        return requirementConditionMapper.toResponse(requirementCondition);
    }

    @Transactional
    public RequirementConditionResponseDTO updateRequirementCondition(Long id, RequirementConditionCreateDTO dto) {
        requirementConditionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("RequirementCondition", id));

        RequirementCondition toUpdate = requirementConditionMapper.toEntity(dto);
        toUpdate.setId(id);

        return requirementConditionMapper.toResponse(requirementConditionRepository.update(toUpdate));
    }

    @Transactional
    public void deleteRequirementCondition(Long id) {
        RequirementCondition requirementCondition = requirementConditionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("RequirementCondition", id));

        requirementConditionRepository.delete(requirementCondition);
    }
}
