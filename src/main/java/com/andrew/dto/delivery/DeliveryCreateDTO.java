package com.andrew.dto.delivery;

import jakarta.validation.constraints.NotNull;

public record DeliveryCreateDTO(
    @NotNull Long supplyId,
    @NotNull Long visitRequestId,
    @NotNull Integer amount
) {}
