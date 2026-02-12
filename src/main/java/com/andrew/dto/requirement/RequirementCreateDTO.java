package com.andrew.dto.requirement;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record RequirementCreateDTO(
    @NotNull Long userId,
    @NotNull Long rewardId,
    @NotNull Long rewardAmount,
    @NotNull LocalDate startDate,
    @NotNull LocalDate untilDate
) {}
