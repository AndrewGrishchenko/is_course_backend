package com.andrew.dto.cases;

import com.andrew.model.Status;

public record CaseResponseDTO(
    Long id,
    String description,
    Status status
) {}
