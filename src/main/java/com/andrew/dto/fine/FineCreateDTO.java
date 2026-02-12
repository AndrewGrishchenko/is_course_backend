package com.andrew.dto.fine;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record FineCreateDTO(
    @NotNull Long caseId,
    @NotNull BigDecimal amount
) {}
