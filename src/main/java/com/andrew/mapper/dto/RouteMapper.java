package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.route.RouteCreateDTO;
import com.andrew.dto.route.RouteResponseDTO;
import com.andrew.model.Route;
import com.andrew.model.RouteRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface RouteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "routeRequest", source = "routeRequestId", qualifiedByName = "routeRequestFromId")
    @Mapping(target = "status", ignore = true)
    Route toEntity(RouteCreateDTO dto);

    @Mapping(target = "routeRequestId", source = "request.id")
    RouteResponseDTO toResponse(Route entity);

    @Named("routeRequestFromId")
    default RouteRequest routeRequestFromId(Long id) {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setId(id);
        return routeRequest;
    }
}
