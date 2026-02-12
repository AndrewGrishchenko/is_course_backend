package com.andrew.dto.supply;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SupplyCreateDTO(
    @NotNull @NotEmpty String name,
    @NotNull Integer amount
) {}
