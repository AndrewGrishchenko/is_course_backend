package com.andrew.dto.requirement;

import java.time.LocalDate;

public record RequirementResponseDTO(
    Long id,
    Long userId,
    Long rewardId,
    Long rewardAmount,
    LocalDate startDate,
    LocalDate untilDate
) {}
