package com.andrew.dto.IncidentReport;

import com.andrew.model.enums.CaseStatus;
import com.andrew.model.enums.SupplyType;

public record IncidentReportResponseDTO(
    Long id,
    Long caseId,
    CaseStatus status,
    String description,
    SupplyType supplyType
) {}
