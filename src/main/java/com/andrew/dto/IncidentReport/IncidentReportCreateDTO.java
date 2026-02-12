package com.andrew.dto.IncidentReport;

import jakarta.validation.constraints.NotNull;

public record IncidentReportCreateDTO(
    @NotNull Long caseId,
    @NotNull Long supplyId
) {}
