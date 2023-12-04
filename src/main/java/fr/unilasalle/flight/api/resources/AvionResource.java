package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Avion;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import fr.unilasalle.flight.api.repository.AvionRepository;

import java.util.ArrayList;
import java.util.List;

@Path("/planes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AvionResource extends GenericResources{
    @Inject
    private AvionRepository repository;
    @Inject
    Validator validator;

    @GET//Récupérer la liste de tous les avions
    public Response getPlanes()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET//Récupérer la liste des avions par constructeur
    @Path("/{operator}")
    public Response getPlanesByOp(@PathParam("operator") String operator)
    {
        List<Avion> list = new ArrayList<>();
        if(StringUtils.isBlank(operator))
            list = repository.listAll();
        else
            list = repository.findByOperator(operator);
        return getOr404(list);
    }

    @GET//Récupérer les informations d'un avion en particulier
    @Path("/{id}")
    public Response getPlanes(@PathParam("id") Long id)
    {
         var avion = repository.
                 findByIdOptional(id).orElse(null);
         return getOr404(avion);
    }

    @POST//Ajout d'un avion
    @Transactional
    public Response createPlane(Avion plane)
    {
        var violations = validator.validate(plane);
        if(!violations.isEmpty())
            return  Response.status(400).entity(new ErrorWrapper(violations)).build();
        try
        {
            repository.persistAndFlush(plane);
            return Response.status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();}
    }
}
