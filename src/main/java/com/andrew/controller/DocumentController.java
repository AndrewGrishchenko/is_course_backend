package com.andrew.controller;

import java.io.InputStream;

import com.andrew.annotation.RequireRole;
import com.andrew.model.DocType;
import com.andrew.model.Role;
import com.andrew.service.DocumentService;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @RequireRole(Role.CAPTAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadDocument(
        @FormParam("file") EntityPart filePart,
        @FormParam("docType") String doc
    ) {
        DocType docType = DocType.from(doc).orElseThrow(() -> new BadRequestException());
        
        if (filePart == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        
        InputStream stream = filePart.getContent();
        String originalName = filePart.getFileName().orElse("unknown");
        documentService.uploadDocument(docType, stream, originalName);

        return Response.ok().build();
    }
}
