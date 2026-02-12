package com.andrew.service;

import com.andrew.dto.IncidentReport.IncidentReportCreateDTO;
import com.andrew.dto.IncidentReport.IncidentReportResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.IncidentReportMapper;
import com.andrew.model.IncidentReport;
import com.andrew.repository.IncidentReportRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class IncidentReportService {
    @Inject
    IncidentReportRepository incidentReportRepository;

    @Inject
    IncidentReportMapper incidentReportMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public IncidentReportResponseDTO createIncidentReport(IncidentReportCreateDTO dto) {
        return createIncidentReport(incidentReportMapper.toEntity(dto));
    }

    @Transactional
    private IncidentReportResponseDTO createIncidentReport(IncidentReport incidentReport) {
        incidentReportRepository.save(incidentReport);
        return incidentReportMapper.toResponse(incidentReport);
    }

    @Transactional
    public IncidentReportResponseDTO updateIncidentReport(Long id, IncidentReportCreateDTO dto) {
        incidentReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("IncidentReport", id));

        IncidentReport toUpdate = incidentReportMapper.toEntity(dto);
        toUpdate.setId(id);

        return incidentReportMapper.toResponse(incidentReportRepository.update(toUpdate));
    }

    @Transactional
    public void deleteIncidentReport(Long id) {
        IncidentReport incidentReport = incidentReportRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("IncidentReport", id));

        incidentReportRepository.delete(incidentReport);
    }
}
