package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.complaint.ComplaintCreateDTO;
import com.andrew.dto.complaint.ComplaintResponseDTO;
import com.andrew.model.Complaint;
import com.andrew.model.RouteRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ComplaintMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "case", ignore = true)
    @Mapping(target = "routeRequest", source = "routeRequestId", qualifiedByName = "routeRequestFromId")
    Complaint toEntity(ComplaintCreateDTO dto);

    @Mapping(target = "caseId", source = "case.id")
    @Mapping(target = "routeRequestId", source = "routeRequest.id")
    @Mapping(target = "description", source = "case.description")
    @Mapping(target = "status", source = "case.status")
    ComplaintResponseDTO toResponse(Complaint entity);

    @Named("routeRequestFromId")
    default RouteRequest routeRequestFromId(Long id) {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setId(id);
        return routeRequest;
    }
}
