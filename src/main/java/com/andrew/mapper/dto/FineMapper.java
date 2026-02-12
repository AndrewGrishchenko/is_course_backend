package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.fine.FineCreateDTO;
import com.andrew.dto.fine.FineResponseDTO;
import com.andrew.model.Case;
import com.andrew.model.Fine;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface FineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "case", source = "caseId", qualifiedByName = "caseFromId")
    Fine toEntity(FineCreateDTO dto);

    @Mapping(target = "caseId", source = "case.id")
    FineResponseDTO toResponse(Fine entity);

    @Named("caseFromId")
    default Case caseFromId(Long id) {
        Case caseValue = new Case();
        caseValue.setId(id);
        return caseValue;
    }
}
