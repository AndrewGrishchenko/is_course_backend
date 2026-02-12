package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.complaint.ComplaintCreateDTO;
import com.andrew.dto.complaint.ComplaintResponseDTO;
import com.andrew.model.Case;
import com.andrew.model.Complaint;
import com.andrew.model.Route;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ComplaintMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "case", source = "caseId", qualifiedByName = "caseFromId")
    @Mapping(target = "route", source = "routeId", qualifiedByName = "routeFromId")
    Complaint toEntity(ComplaintCreateDTO dto);

    @Mapping(target = "caseId", source = "case.id")
    @Mapping(target = "routeId", source = "route.id")
    ComplaintResponseDTO toResponse(Complaint entity);

    @Named("caseFromId")
    default Case caseFromId(Long id) {
        Case caseValue = new Case();
        caseValue.setId(id);
        return caseValue;
    }

    @Named("routeFromId")
    default Route routeFromId(Long id) {
        Route route = new Route();
        route.setId(id);
        return route;
    }
}
