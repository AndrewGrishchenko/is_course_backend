package com.andrew.dto.VisitRequest;

import java.time.LocalDate;

import com.andrew.model.Status;

public record VisitRequestResponseDTO(
    Long id,
    Long userId,
    LocalDate date,
    Status status
) {}