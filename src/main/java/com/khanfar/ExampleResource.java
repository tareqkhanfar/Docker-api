package com.khanfar;

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
    public Response createClientEnvironment(EnvironmentDescription environmentDescription) {

        System.out.println(123);
        try {
            dockerService.createClientEnvironment(environmentDescription);
            return Response.ok("<h1>Environment created IS DONE  for client: " + environmentDescription.getClientName() +"</h1> \n <h1> IP Address : localhost:"+DockerService.lastPort+"</h1>").build();
        } catch (Exception e) {
             e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
