package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repository.PassengerRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Path("/passengers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PassengerResource extends GenericResources{
    @Inject
    private PassengerRepository repository;
    @Inject
    Validator validator;

    @GET
    public Response getPassenger()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET
    public Response getPassengerByOp(@PathParam("operator") String operator)
    {
        List<Passenger> list = new ArrayList<>();
        if(StringUtils.isBlank(operator))
            list = repository.listAll();
        else
            list = repository.findByOperator(operator);
        return getOr404(list);
    }

    @GET
    @Path("/{id}")
    public Response getPassenger(@PathParam("id") Long id)
    {
        var passenger = repository.
                findByIdOptional(id).orElse(null);
        return getOr404(passenger);
    }

    @POST
    @Transactional
    public Response createPassenger(Passenger passenger)
    {
        var violations = validator.validate(passenger);
        if(!violations.isEmpty())
            return  Response.status(400).entity(new ErrorWrapper(violations)).build();
        try
        {
            repository.persistAndFlush(passenger);
            return Response.status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();}
    }
}
