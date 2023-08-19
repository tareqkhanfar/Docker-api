package com.khanfar.Controller;


import com.khanfar.DTO.EnvironmentDTO;
import com.khanfar.Service.EnvironmentService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/env")
public class EnvController {

    @Inject
    EnvironmentService environmentService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response fetchAllContainers() {
        return Response.ok(environmentService.fetchAllContainers()).build();
    }

    @GET
    @Path("/{containerName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response fetchEnvironmentByName(String containerName) {
        return Response.ok(environmentService.fetchEnvironmentByName(containerName)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response insert(EnvironmentDTO environmentDTO) {
        return Response.ok(environmentService.insert(environmentDTO)).build();
    }
}
