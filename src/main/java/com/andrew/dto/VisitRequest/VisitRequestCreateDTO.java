package com.andrew.dto.VisitRequest;

import java.time.LocalDate;

import com.andrew.model.Status;

import jakarta.validation.constraints.NotNull;

public record VisitRequestCreateDTO(
    @NotNull Long userId,
    @NotNull LocalDate date,
    @NotNull Status status
) {}
