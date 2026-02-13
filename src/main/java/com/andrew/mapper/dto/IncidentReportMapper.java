package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.IncidentReport.IncidentReportCreateDTO;
import com.andrew.dto.IncidentReport.IncidentReportResponseDTO;
import com.andrew.model.Case;
import com.andrew.model.IncidentReport;
import com.andrew.model.Supply;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface IncidentReportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "case", ignore = true)
    IncidentReport toEntity(IncidentReportCreateDTO dto);

    @Mapping(target = "caseId", source = "case.id")
    IncidentReportResponseDTO toResponse(IncidentReport entity);

    @Named("caseFromId")
    default Case caseFromId(Long id) {
        Case caseValue = new Case();
        caseValue.setId(id);
        return caseValue;
    }

    @Named("supplyFromId")
    default Supply supplyFromId(Long id) {
        Supply supply = new Supply();
        supply.setId(id);
        return supply;
    }
}
