package com.andrew.service;

import com.andrew.dto.document.DocumentCreateDTO;
import com.andrew.dto.document.DocumentResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.DocumentMapper;
import com.andrew.model.Document;
import com.andrew.repository.DocumentRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DocumentService {
    @Inject
    DocumentRepository documentRepository;

    @Inject
    DocumentMapper documentMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public DocumentResponseDTO getById(Long id) {
        Document document = documentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Document", id));
        return documentMapper.toResponse(document);
    }

    @Transactional
    public DocumentResponseDTO createDocument(DocumentCreateDTO dto) {
        return createDocument(documentMapper.toEntity(dto));
    }

    @Transactional
    private DocumentResponseDTO createDocument(Document document) {
        documentRepository.save(document);
        return documentMapper.toResponse(document);
    }

    @Transactional
    public DocumentResponseDTO updateDocument(Long id, DocumentCreateDTO dto) {
        documentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Document", id));

        Document toUpdate = documentMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return documentMapper.toResponse(documentRepository.update(toUpdate));
    }

    @Transactional
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Document", id));

        documentRepository.delete(document);
    }
}
