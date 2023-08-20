package com.khanfar;

import com.khanfar.DTO.ClientEnvironmentDTO;
import com.khanfar.DTO.EnvironmentDTO;
import com.khanfar.DTO.UserDTO;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Service.DockerService;
import com.khanfar.config.MyConfiguration;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/docker")
public class ExampleResource {


    @Inject
    MyConfiguration configuration;

    @Inject
    DockerService dockerService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createClientEnvironment(ClientEnvironmentDTO clientEnvironmentDTO) {

        try {
            System.out.println(clientEnvironmentDTO.getEnvironmentDTO());
            System.out.println(clientEnvironmentDTO.getUserDTO());
            dockerService.createClientEnvironment(clientEnvironmentDTO.getUserDTO(), clientEnvironmentDTO.getEnvironmentDTO());
            return Response.ok("<h1>Environment created IS DONE  for client: " + clientEnvironmentDTO.getEnvironmentDTO().getLabelName() +"</h1> \n <h1> IP Address : http://49.13.66.183/:"+DockerService.lastPort+"</h1>").build();
        } catch (Exception e) {
             e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContainer(EnvironmentDTO environmentDescription) {
        try {
            return Response.ok(dockerService.deleteContainer(environmentDescription)).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/container/{containerName}")
    @Produces(MediaType.APPLICATION_JSON)
public Response getContainerByName(@PathParam("containerName") String containerName) {
        return Response.ok(dockerService.getContainerByName(containerName)).build();
    }

    @GET
    @Path("/container")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContainers() {
        return Response.ok(dockerService.getAllContainers()).build();
    }
    @GET
    @Path("/volume")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVolumes() {
        return Response.ok(dockerService.getAllVolumes()).build();
    }
    @GET
    @Path("/network")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNetworks() {
        return Response.ok(dockerService.getAllNetworks()).build();
    }
    @GET
    @Path("/container/status/{containerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus(@PathParam("containerName") String containerName) {
        return Response.ok(dockerService.getStatus(containerName)).build();
    }
    @GET
    @Path("/container/state/{containerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getState(@PathParam("containerName") String containerName) {
        return Response.ok(dockerService.getState(containerName)).build();

    }



    @POST
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response stopContainer(EnvironmentDTO environmentDescription) {
        try {
            return Response.ok(dockerService.stopContainer(environmentDescription)).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startContainer(EnvironmentDTO environmentDescription) {
        try {
            return Response.ok(dockerService.startContainer(environmentDescription)).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/config")
    public Response getConfiguration() {
        JsonObject configJson = Json.createObjectBuilder()
                .add("Database URL", configuration.getDatabaseUrl())
                .add("Database Name", configuration.getDatabaseName())
                .add("Database User", configuration.getDatabaseUser())
                .add("Database Password", configuration.getDatabasePassword())
                .add("Database Prefix", configuration.getDatabasePrefix())
                .add("Network Prefix", configuration.getNetworkPrefix())
                .add("Service Prefix", configuration.getServicePrefix())
                .add("Volume Prefix", configuration.getVolumePrefix())
                .add("Host Path", configuration.getHostPath())
                .add("Container Path", configuration.getContainerPath())
                .add("Database Exposed Port", configuration.getDatabaseExposedPort())
                .add("Database Path", configuration.getDatabasePath())
                .add("Service Image Name", configuration.getServiceImageName())
                .add("Database Image Name", configuration.getDatabaseImageName())
                .add("Service Port", configuration.getServicePort())
                .build();

        return Response.ok(configJson).build();
    }


}
