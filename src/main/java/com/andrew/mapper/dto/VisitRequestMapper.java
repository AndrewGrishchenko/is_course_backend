package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.VisitRequest.VisitRequestCreateDTO;
import com.andrew.dto.VisitRequest.VisitRequestResponseDTO;
import com.andrew.model.User;
import com.andrew.model.VisitRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface VisitRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    VisitRequest toEntity(VisitRequestCreateDTO dto);

    @Mapping(target = "userId", source = "user.id")
    VisitRequestResponseDTO toResponse(VisitRequest entity);

    @Named("userFromId")
    default User userFromId(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
