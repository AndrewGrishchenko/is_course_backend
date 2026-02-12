package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.andrew.dto.supply.SupplyCreateDTO;
import com.andrew.dto.supply.SupplyResponseDTO;
import com.andrew.model.Supply;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface SupplyMapper {
    @Mapping(target = "id", ignore = true)
    Supply toEntity(SupplyCreateDTO dto);

    SupplyResponseDTO toResponse(Supply entity);
}
