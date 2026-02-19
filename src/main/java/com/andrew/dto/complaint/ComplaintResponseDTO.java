package com.andrew.dto.complaint;

public record ComplaintResponseDTO(
    Long id,
    Long caseId,
    String description,
    Long routeRequestId
) {}
