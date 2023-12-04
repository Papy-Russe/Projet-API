package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Vol;
import fr.unilasalle.flight.api.repository.AvionRepository;
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
    private AvionRepository avionRepository;

    @Inject
    Validator validator;

    @GET//Récupérer la liste des vols
    public Response getFlights()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET//Récupérer la liste des vols par destinations
    @Path("/{destination}")
    public Response getFlightsByDest(@PathParam("destination") String destination)
    {
        List<Vol> list = new ArrayList<>();
        if(StringUtils.isBlank(destination))
            list = repository.listAll();
        else
            list = repository.findByDestination(destination);
        return getOr404(list);
    }
    //RECHERCHE PAR ID
    @GET//Récupérer la liste des vols par id
    @Path("/{id}")
    public Response getFlights(@PathParam("id") Long id)
    {
        var vol = repository.findByIdOptional(id).orElse(null);
        return getOr404(vol);
    }

    @POST//Ajouter un vol
    @Transactional
    public Response createFlights(Vol flight)
    {
        var violations = validator.validate(flight);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();

        var idAvion = flight.getPlane_id().getId();
        var avion = avionRepository.findByIdOptional(idAvion).orElse(null);

        if(avion != null){
            flight.setPlane_id(avion);
        }else{
            // REVOYER UNE ERREUR
        }
        try
        {
            repository.persistAndFlush(flight);
            return Response.status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}
