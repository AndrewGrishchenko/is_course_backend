package com.andrew.service;

import com.andrew.dto.delivery.DeliveryCreateDTO;
import com.andrew.dto.delivery.DeliveryResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.DeliveryMapper;
import com.andrew.model.Delivery;
import com.andrew.repository.DeliveryRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeliveryService {
    @Inject
    DeliveryRepository deliveryRepository;

    @Inject
    DeliveryMapper deliveryMapper;

    @Inject
    private SecurityContext securityContext;

    @Transactional
    public DeliveryResponseDTO createDelivery(DeliveryCreateDTO dto) {
        return createDelivery(deliveryMapper.toEntity(dto));
    }

    @Transactional
    public DeliveryResponseDTO createDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
        return deliveryMapper.toResponse(delivery);
    }

    @Transactional
    public DeliveryResponseDTO updateDelivery(Long id, DeliveryCreateDTO dto) {
        deliveryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Delivery", id));

        Delivery toUpdate = deliveryMapper.toEntity(dto);
        toUpdate.setId(id);

        return deliveryMapper.toResponse(deliveryRepository.update(toUpdate));
    }

    @Transactional
    public void deleteDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Delivery", id));

        deliveryRepository.delete(delivery);
    }
}
