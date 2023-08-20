package com.khanfar.Controller;


import com.khanfar.DTO.UserDTO;
import com.khanfar.Service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/registration")
public class UserController {


    @Inject
    UserService userService ;

    @Transactional
    @POST
    @Path("/register")
    public Response register(UserDTO user) {
       try {
           return Response.ok(userService.register(user)).build();
       }
       catch (Exception e ) {
           return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
       }
    }



    @Transactional
    @POST
    @Path("/login")
    public Response login(UserDTO user) {


        try {
            return Response.ok( userService.login(user)).build();

        }
        catch (Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    @Path("/{username}")
    public Response getAllContainers (@PathParam("username") String username) {
        try {
            return Response.ok(userService.findContainerNameByUsername(username)).build();
        }
        catch (Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
