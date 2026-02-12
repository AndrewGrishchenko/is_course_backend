package com.andrew.service;

import com.andrew.dto.complaint.ComplaintCreateDTO;
import com.andrew.dto.complaint.ComplaintResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.ComplaintMapper;
import com.andrew.model.Complaint;
import com.andrew.repository.ComplaintRepository;
import com.andrew.security.CurrentUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ComplaintService {
    @Inject
    ComplaintRepository complaintRepository;

    @Inject
    ComplaintMapper complaintMapper;

    @Inject
    CurrentUser currentUser;

    @Transactional
    public ComplaintResponseDTO createComplaint(ComplaintCreateDTO dto) {
        return createComplaint(complaintMapper.toEntity(dto));
    }

    @Transactional
    private ComplaintResponseDTO createComplaint(Complaint complaint) {
        complaintRepository.save(complaint);
        return complaintMapper.toResponse(complaint);
    }

    @Transactional
    public ComplaintResponseDTO updateComplaint(Long id, ComplaintCreateDTO dto) {
        complaintRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Complaint", id));

        Complaint toUpdate = complaintMapper.toEntity(dto);
        toUpdate.setId(id);

        return complaintMapper.toResponse(complaintRepository.update(toUpdate));
    }

    @Transactional
    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Complaint", id));

        complaintRepository.delete(complaint);
    }
}
