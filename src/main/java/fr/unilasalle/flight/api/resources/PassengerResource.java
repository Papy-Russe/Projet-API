package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repository.PassengerRepository;
import fr.unilasalle.flight.api.repository.ReservationRepository;
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

    @GET//récupération de tous les passagers
    public Response getPassenger()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET//récupération d'un passagers en particulier
    @Path("/{id}")
    public Response getPassenger(@PathParam("id") Long id)
    {
        var passenger = repository.
                findByIdOptional(id).orElse(null);
        return getOr404(passenger);
    }

    @PUT//Modifier un passager
    @Path("/{id}")
    @Transactional
    public Response updatePassenger(@PathParam("id") Long id, Passenger updatedPassenger)
    {
        var violations = validator.validate(id);
        if(!violations.isEmpty())
            return  Response.status(400).entity(new ErrorWrapper(violations)).build();

        var passenger = repository.findByIdOptional(id).orElse(null);

        if(passenger != null)
        {
            passenger.setFirstname(updatedPassenger.getFirstname());
            passenger.setSurname(updatedPassenger.getSurname());
            passenger.setEmailaddress(updatedPassenger.getEmailaddress());
            try{
                repository.persistAndFlush(passenger);
                return Response.status(200).build();
            }catch (PersistenceException e) {
                return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
            }
        }else
            return Response.status(404).entity(new ErrorWrapper("Passenger not found")).build();
    }

    @POST//Ajouter un passager
    @Transactional
    public Response createPasssenger(Passenger passengerToCreate)
    {
        var violations = validator.validate(passengerToCreate);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();

        try{
            repository.persistAndFlush(passengerToCreate);
            return Response.status(201).build();
        }catch (PersistenceException e){
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}
