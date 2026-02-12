package com.andrew.controller;

import java.net.URI;

import com.andrew.dto.document.DocumentCreateDTO;
import com.andrew.dto.document.DocumentResponseDTO;
import com.andrew.service.DocumentService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentController {
    @Inject
    DocumentService documentService;

    @GET
    @Path("{id}")
    @RolesAllowed({"CAPTAIN", "KEEPER"})
    public Response getDocumentById(@PathParam("id") Long id) {
        return Response.ok(documentService.getById(id)).build();
    }

    @POST
    @RolesAllowed("CAPTAIN")
    public Response createDocument(@Valid DocumentCreateDTO dto, @Context UriInfo uriInfo) {
        DocumentResponseDTO created = documentService.createDocument(dto);

        URI location = uriInfo.getAbsolutePathBuilder()
            .path(String.valueOf(created.id()))
            .build();

        return Response.created(location)
            .entity(created)
            .build();
    }
}
