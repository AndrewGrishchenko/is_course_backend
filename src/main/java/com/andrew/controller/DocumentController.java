package com.andrew.controller;

import java.io.InputStream;
import java.net.URI;

import com.andrew.annotation.RequireRole;
import com.andrew.dto.document.DocumentCreateDTO;
import com.andrew.dto.document.DocumentResponseDTO;
import com.andrew.model.DocType;
import com.andrew.model.Role;
import com.andrew.service.DocumentService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.EntityPart;
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
    @RequireRole({Role.ADMIN, Role.CAPTAIN})
    public Response getDocuments(@PathParam("id") Long id) {
        return Response.ok(documentService.getAll()).build();
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

    // @POST
    // @RequireRole(Role.CAPTAIN)
    // public Response uploadDocument(
    //     @FormParam("file") EntityPart filePart,
    //     @FormParam("docType") DocType docType
    // ) {
    //     if (filePart == null || docType == null)
    //         return Response.status(Response.Status.BAD_REQUEST).build();

    //     try (InputStream stream = filePart.getContent()) {
    //         String originalName = filePart.getFileName().orElse("unknown");
    //         documentService.uploadDocument(docType, stream, originalName);

    //         return Response.ok().build();
    //     } catch (Exception e) {
    //         return Response.status(Response.Status.BAD_REQUEST).build();
    //     }
    // }
}
