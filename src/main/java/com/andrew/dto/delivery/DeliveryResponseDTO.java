package com.andrew.dto.delivery;

public record DeliveryResponseDTO(
    Long id,
    Long supplyId,
    Long visitRequestId,
    Integer amount
) {}
