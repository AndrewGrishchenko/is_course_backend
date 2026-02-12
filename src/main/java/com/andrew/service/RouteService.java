package com.andrew.service;

import java.sql.SQLException;

import com.andrew.dto.route.RouteBuildDTO;
import com.andrew.dto.route.RouteCreateDTO;
import com.andrew.dto.route.RouteResponseDTO;
import com.andrew.exceptions.NotFoundException;
import com.andrew.exceptions.RouteBuildException;
import com.andrew.mapper.dto.RouteMapper;
import com.andrew.model.Route;
import com.andrew.model.enums.RouteRequestStatus;
import com.andrew.model.enums.RouteStatus;
import com.andrew.repository.RouteRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;

@ApplicationScoped
public class RouteService {
    @Inject
    RouteRepository routeRepository;

    @Inject
    RouteRequestService routeRequestService;

    @Inject
    RouteMapper routeMapper;

    @Transactional
    public RouteResponseDTO createRoute(RouteCreateDTO dto) {
        if (routeRepository.existsByRequestId(dto.routeRequestId()))
            throw new ForbiddenException();

        if (!routeRequestService.checkStatus(dto.routeRequestId(), RouteRequestStatus.SUBMITTED))
            throw new ForbiddenException();
        
        return createRoute(routeMapper.toEntity(dto));
    }

    @Transactional
    private RouteResponseDTO createRoute(Route route) {
        routeRepository.save(route);
        return routeMapper.toResponse(route);
    }

    @Transactional
    public void buildRoute(Long id, RouteBuildDTO dto) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Route", id));

        if (!route.getStatus().equals(RouteStatus.DRAFT))
            throw new ForbiddenException();

        try {
            routeRepository.buildRoute(id, dto.zoneIds());
        // } catch (Exception e) {
        //     throw new RouteBuildException("Failed to build route: " + e.getMessage());
        // }
        } catch (Exception ex) {
            String msg = extractSqlErrorMessage(ex);
            throw new RouteBuildException("Failed to build route: " + msg);
        }
    }

    private String extractSqlErrorMessage(Throwable t) {
        if (t == null) return "Unknown error";

        if (t instanceof org.hibernate.exception.GenericJDBCException gjex) {
            SQLException sqlEx = gjex.getSQLException();
            if (sqlEx != null) {
                return getPostgresMessage(sqlEx);
            }
        } else if (t instanceof java.sql.SQLException sqle) {
            return getPostgresMessage(sqle);
        }

        return extractSqlErrorMessage(t.getCause());
    }

    private String getPostgresMessage(SQLException sqlEx) {
        StringBuilder sb = new StringBuilder();
        while (sqlEx != null) {
            String msg = sqlEx.getMessage();
            if (msg != null && !msg.isEmpty()) {
                // вычленяем текст после "ОШИБКА: " до перевода строки
                String[] lines = msg.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.startsWith("ОШИБКА:")) {
                        sb.append(line.substring("ОШИБКА:".length()).trim());
                    }
                }
            }
            sqlEx = sqlEx.getNextException();
        }
        return sb.toString().trim();
    }

    @Transactional
    public void approveRoute(Long id) {
        Route route = getById(id);

        if (!route.getStatus().equals(RouteStatus.DRAFT))
            throw new ForbiddenException();

        route.setStatus(RouteStatus.APPROVED);
        route.getRequest().setStatus(RouteRequestStatus.APPROVED);
        
        routeRepository.update(route);
    }

    @Transactional
    public void rejectRoute(Long id) {
        Route route = getById(id);

        if (!route.getStatus().equals(RouteStatus.DRAFT))
            throw new ForbiddenException();

        route.setStatus(RouteStatus.APPROVED);
        route.getRequest().setStatus(RouteRequestStatus.APPROVED);

        routeRepository.update(route);
    }

    @Transactional
    public void startRoute(Long id) {
        Route route = getById(id);

        if (!route.getStatus().equals(RouteStatus.APPROVED))
            throw new ForbiddenException();

        route.setStatus(RouteStatus.IN_PROGRESS);

        routeRepository.update(route);
    }

    @Transactional
    public void completeRoute(Long id) {
        Route route = getById(id);

        if (!route.getStatus().equals(RouteStatus.IN_PROGRESS))
            throw new ForbiddenException();

        route.setStatus(RouteStatus.COMPLETED);

        routeRepository.update(route);
    }

    @Transactional
    public Route getById(Long id) {
        return routeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Route", id));
    }

    @Transactional
    public RouteResponseDTO updateRoute(Long id, RouteCreateDTO dto) {
        routeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Route", id));

        Route toUpdate = routeMapper.toEntity(dto);
        toUpdate.setId(id);
        
        return routeMapper.toResponse(routeRepository.update(toUpdate));
    }

    @Transactional
    public void deleteRoute(Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Route", id));
        
        routeRepository.delete(route);
    }
}
