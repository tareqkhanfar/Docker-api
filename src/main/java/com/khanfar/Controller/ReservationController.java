package com.khanfar.Controller;


import com.khanfar.DTO.User_EnvDTO;
import com.khanfar.Service.ReservationService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reservation")
public class ReservationController {

    @Inject
    ReservationService reservationService ;


    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reserve(User_EnvDTO user_envDTO) {
        return Response.ok(reservationService.newReserve(user_envDTO)).build();
    }
}
