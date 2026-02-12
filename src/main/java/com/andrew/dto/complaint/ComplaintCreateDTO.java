package com.andrew.dto.complaint;

import jakarta.validation.constraints.NotNull;

public record ComplaintCreateDTO(
    @NotNull Long caseId,
    @NotNull Long routeId
) {}
