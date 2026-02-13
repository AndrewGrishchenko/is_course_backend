package com.andrew.service;

import com.andrew.dto.complaint.ComplaintCreateDTO;
import com.andrew.dto.complaint.ComplaintResponseDTO;
import com.andrew.dto.fine.FineCreateDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.ComplaintMapper;
import com.andrew.model.Case;
import com.andrew.model.Complaint;
import com.andrew.model.enums.CaseStatus;
import com.andrew.repository.ComplaintRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ComplaintService {
    @Inject
    ComplaintRepository complaintRepository;

    @Inject
    ComplaintMapper complaintMapper;

    @Inject
    RouteManagementService routeManagementService;

    @Inject
    CaseManagementService caseManagementService;

    @Inject
    FineService fineService;

    //TODO: maybe check uniqueness in logic, here, instead of postgres exception
    @Transactional
    public ComplaintResponseDTO createComplaint(ComplaintCreateDTO dto) {
        routeManagementService.assertCanBeRequestComplained(dto.routeRequestId());
        
        Case c = caseManagementService.createCase(dto.description());
        Complaint complaint = complaintMapper.toEntity(dto);
        complaint.setCase(c);
        
        complaintRepository.save(complaint);
        return complaintMapper.toResponse(complaint);
    }

    @Transactional
    public Complaint getBydId(Long id) {
        return complaintRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Complaint", id));
    }

    @Transactional
    public void closeComplaint(Long id) {
        Complaint complaint = getBydId(id);
        caseManagementService.closeCase(complaint.getCase());
    }

    @Transactional
    public void fineComplaint(Long id, FineCreateDTO dto) {
        Complaint complaint = getBydId(id);

        fineService.createFine(complaint.getCase(), dto.amount());

        caseManagementService.closeCase(complaint.getCase());
    }

    //TODO: do we need this fr?
    // @Transactional
    // public ComplaintResponseDTO updateComplaint(Long id, ComplaintCreateDTO dto) {
    //     complaintRepository.findById(id)
    //         .orElseThrow(() -> new NotFoundException("Complaint", id));

    //     Complaint toUpdate = complaintMapper.toEntity(dto);
    //     toUpdate.setId(id);

    //     return complaintMapper.toResponse(complaintRepository.update(toUpdate));
    // }

    // @Transactional
    // public void deleteComplaint(Long id) {
    //     Complaint complaint = complaintRepository.findById(id)
    //         .orElseThrow(() -> new NotFoundException("Complaint", id));

    //     complaintRepository.delete(complaint);
    // }
}
