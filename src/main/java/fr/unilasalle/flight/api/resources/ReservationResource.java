package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repository.ReservationRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ReservationResource extends GenericResources{
    @Inject
    private ReservationRepository repository;
    @Inject
    Validator validator;

    @GET
    public Response getReservations()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET
    public Response getReservationsByOp(@PathParam("operator") String operator)
    {
        List<Reservation> list = new ArrayList<>();
        if(StringUtils.isBlank(operator))
            list = repository.listAll();
        else
            list = repository.findByOperator(operator);
        return getOr404(list);
    }

    @POST
    @Transactional
    public Response createReservations(Reservation reservation)
    {
        var violations = validator.validate(reservation);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        try
        {
            repository.persistAndFlush(reservation);
            return Response.status(201).build();
        }
        catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}
