package com.andrew.dto.route;

import com.andrew.model.enums.RouteStatus;

public record RouteResponseDTO(
    Long id,
    Long routeRequestId,
    RouteStatus status
) {}
