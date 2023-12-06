package fr.unilasalle.flight.api.resources;

import fr.unilasalle.flight.api.beans.Avion;
import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repository.PassengerRepository;
import fr.unilasalle.flight.api.repository.ReservationRepository;
import fr.unilasalle.flight.api.repository.VolsRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;


import javax.imageio.plugins.jpeg.JPEGImageReadParam;
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
    private VolsRepository volsRepository;
    @Inject
    private PassengerRepository passengerRepository;
    @Inject
    Validator validator;

    @GET//Récupération de la liste de toutes les résertvations
    public Response getReservations()
    {
        var list = repository.listAll();
        return getOr404(list);
    }

    @GET//Récuération d'une réservation en particule=ier en fonction de l'id
    @Path("/{id}")
    public Response getReservationsById(@PathParam("id") Long id)
    {
        var reservation = repository.findByIdOptional(id).orElse(null);
        return getOr404(reservation);
    }

    @GET//Récupération de toutes les réservations sur un seul vol
    @Path("/flight/{flight_id}")
    public Response getReservationsByFlights(@PathParam("flight_id") String flight_number)
    {
        List<Reservation> list = new ArrayList<>();
        if(StringUtils.isBlank(flight_number))
            list = repository.listAll();
        else
            list = repository.findByFlights(flight_number);
        return getOr404(list);
    }

    @POST//Ajouter une réservation
    @Transactional
    public Response createReservations(Reservation reservation)
    {
        var violations = validator.validate(reservation);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();

        //vérification existence vol
        var flight = volsRepository.findByIdOptional(reservation.getFlight_id().getId()).orElse(null);
        //vérification existence passenger
        var passenger = passengerRepository.findByIdOptional(reservation.getPassenger_id().getId()).orElse(null);

        if(flight != null && passenger != null)
        {
            reservation.setFlight_id(flight);//Association d'un vol à la réservation
            reservation.setPassenger_id(passenger);//Association d'un passager à la réservation
            try{
                repository.persistAndFlush(reservation);
                return Response.status(201).build();
            }catch (PersistenceException e){
                return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
            }
        }else{
            return Response.status(400).build();//mettre un vrai message d'erreur
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") Long idReservation)
    {
        var violations = validator.validate(idReservation);
        if(!violations.isEmpty())
            return Response.status(400).entity(new ErrorWrapper(violations)).build();

        var reservation = repository.findByIdOptional(idReservation).orElse(null);
        if(reservation != null)//si le vol est trouvé
        {
            try{
                repository.delete(reservation);//suppression de la réservation
                return Response.status(200).build();//retour de la requête
            }catch (PersistenceException e) {
                return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
            }
        }
        else
            return getOr404(reservation);//le vol existe pas
    }
}
