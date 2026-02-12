package com.andrew.dto.RouteRequest;

import java.util.List;

import com.andrew.model.Goal;

public record RouteRequestResponseDTO(
    Long id,
    Long shipId,
    Long sourceZoneId,
    Long targetZoneId,
    Goal goal,
    List<Long> documentsIds
) {}
