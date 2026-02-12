package com.andrew.controller;

import com.andrew.service.ZoneService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

//TODO: hz
@Path("/zone")
@RolesAllowed("ADMIN")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZoneController {
    @Inject
    ZoneService zoneService;

    @GET
    public Response getAll() {
        return Response.ok(zoneService.getAll()).build();
    }
}
