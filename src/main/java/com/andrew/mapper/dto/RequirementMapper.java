package com.andrew.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import com.andrew.dto.requirement.RequirementCreateDTO;
import com.andrew.dto.requirement.RequirementResponseDTO;
import com.andrew.model.Requirement;
import com.andrew.model.Supply;
import com.andrew.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface RequirementMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    @Mapping(target = "reward", source = "rewardId", qualifiedByName = "supplyFromId")
    Requirement toEntity(RequirementCreateDTO dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "rewardId", source = "reward.id")
    RequirementResponseDTO toResponse(Requirement entity);

    @Named("userFromId")
    default User userFromId(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("supplyFromId")
    default Supply supplyFromId(Long id) {
        Supply supply = new Supply();
        supply.setId(id);
        return supply;
    }
}
