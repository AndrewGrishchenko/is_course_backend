package com.andrew.dto.VisitRequest;

import java.time.LocalDate;

import com.andrew.model.enums.VisitRequestStatus;

public record VisitRequestResponseDTO(
    Long id,
    Long userId,
    LocalDate date,
    VisitRequestStatus status
) {}