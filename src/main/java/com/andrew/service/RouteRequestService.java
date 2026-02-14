package com.andrew.service;

import com.andrew.dto.RouteRequest.RouteRequestCreateDTO;
import com.andrew.dto.RouteRequest.RouteRequestResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.mapper.dto.RouteRequestMapper;
import com.andrew.model.RouteRequest;
import com.andrew.model.enums.RouteRequestStatus;
import com.andrew.repository.RouteRequestRepository;
import com.andrew.security.CurrentUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;

@ApplicationScoped
public class RouteRequestService {
    @Inject
    RouteRequestRepository routeRequestRepository;

    @Inject
    RouteRequestMapper routeRequestMapper;

    @Inject
    CurrentUser currentUser;

    @Inject
    ShipService shipService;

    @Transactional
    public RouteRequest getById(Long id) {
        return routeRequestRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("RouteRequest", id));
    }

    @Transactional
    public RouteRequestResponseDTO createRouteRequest(RouteRequestCreateDTO dto) {
        if (!shipService.isUserOwnsShip(dto.shipId()))
            throw new ForbiddenException();
        
        return createRouteRequest(routeRequestMapper.toEntity(dto));
    }

    @Transactional
    private RouteRequestResponseDTO createRouteRequest(RouteRequest routeRequest) {
        routeRequestRepository.save(routeRequest);
        return routeRequestMapper.toResponse(routeRequest);
    }

    @Transactional
    public RouteRequestResponseDTO updateRouteRequest(Long id, RouteRequestCreateDTO dto) {
        RouteRequest existing = getById(id);
        
        if (!existing.getStatus().equals(RouteRequestStatus.DRAFT))
            throw new ForbiddenException();
        
        if (!shipService.isUserOwnsShip(dto.shipId()))
            throw new ForbiddenException();

        RouteRequest toUpdate = routeRequestMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return routeRequestMapper.toResponse(routeRequestRepository.update(toUpdate));
    }

    @Transactional
    public void assertStatus(Long id, RouteRequestStatus status) {
        if (!getById(id).getStatus().equals(status))
            throw new ForbiddenException();
    }

    @Transactional
    public void deleteRouteRequest(Long id) {
        RouteRequest routeRequest = getById(id);
        
        routeRequestRepository.delete(routeRequest);
    }
}
