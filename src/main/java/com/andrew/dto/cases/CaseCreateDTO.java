package com.andrew.dto.cases;

import com.andrew.model.Status;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CaseCreateDTO(
    @NotNull @NotEmpty String description,
    @NotNull Status status
) {}
