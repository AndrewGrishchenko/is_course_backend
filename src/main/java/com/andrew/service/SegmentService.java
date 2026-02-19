package com.andrew.service;

import com.andrew.dto.segment.SegmentCreateDTO;
import com.andrew.dto.segment.SegmentResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.SegmentMapper;
import com.andrew.model.Segment;
import com.andrew.repository.SegmentRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

//TODO: about to remove
@ApplicationScoped
public class SegmentService {
    @Inject
    SegmentRepository segmentRepository;

    @Inject
    SegmentMapper segmentMapper;

    @Transactional
    public SegmentResponseDTO createSegment(SegmentCreateDTO dto) {
        return createSegment(segmentMapper.toEntity(dto));
    }

    @Transactional
    private SegmentResponseDTO createSegment(Segment segment) {
        segmentRepository.save(segment);
        return segmentMapper.toResponse(segment);
    }

    @Transactional
    public SegmentResponseDTO updateShip(Long id, SegmentCreateDTO dto) {
        segmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Segment", id));
        
        Segment toUpdate = segmentMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return segmentMapper.toResponse(segmentRepository.update(toUpdate));
    }

    @Transactional
    public void deleteSegment(Long id) {
        Segment segment = segmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Segment", id));
        
        segmentRepository.delete(segment);
    }
}
