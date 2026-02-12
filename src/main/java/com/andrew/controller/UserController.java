package com.andrew.controller;

import com.andrew.annotation.RequireRole;
import com.andrew.dto.user.UserCreateDTO;
import com.andrew.dto.user.UserCreateResponseDTO;
import com.andrew.dto.user.UserLoginDTO;
import com.andrew.dto.user.UserLoginResponseDTO;
import com.andrew.model.Role;
import com.andrew.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    UserService userService;

    // @Inject
    // private SecurityContext securityContext;

    @POST
    @Path("/register")
    @RequireRole(Role.ADMIN)
    public Response register(@Valid UserCreateDTO request) {
        UserCreateResponseDTO response = userService.register(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid UserLoginDTO request) {
        userService.findByUsername(request.username())
                               .orElseThrow(() -> new NotFoundException("User with name " + request.username() + " not found"));
        
        UserLoginResponseDTO response = userService.login(request);
        return Response.ok(response).build();
    }

    // @GET
    // @Path("/user")
    // public Response getAll() {
    //     // PageResponse<User> pagedResult = userService.getAllUsers(mine, page, size, sort, order);

    //     // List<OwnerResponse> responseList = pagedResult.content().stream()
    //     //     .map(ResponseMapper::toResponse)
    //     //     .collect(Collectors.toList());

    //     // PageResponse<OwnerResponse> response = new PageResponse<>(
    //     //     responseList,
    //     //     pagedResult.totalElements()
    //     // );

    //     // return Response.ok(response).build();
    // }
}
