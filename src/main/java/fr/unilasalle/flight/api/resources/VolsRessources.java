package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Vol;
import fr.unilasalle.flight.api.repository.VolsRepository;
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

@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class VolsRessources extends GenericResources{
    @Inject
    private VolsRepository repository;

    @Inject
    Validator validator;

    @GET
    public Response getPlanes()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET
    public Response getFlightsByOp(@PathParam("operator") String operator)
    {
        List<Vol> list = new ArrayList<>();
        if(StringUtils.isBlank(operator))
            list = repository.listAll();
        else
            list = repository.findByOperator(operator);
        return getOr404(list);
    }
    //RECHERCHE PAR ID
    @GET
    @Path("/id")
    public Response getFlights(@PathParam("id") Long id)
    {
        var vol = repository.findByIdOptional(id).orElse(null);
        return getOr404(vol);
    }

    @POST
    @Transactional
    public Response createFlights(Vol flight)
    {
        var violations = validator.validate(flight);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();

        try
        {
            repository.persistAndFlush(flight);
            return Response.status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}
