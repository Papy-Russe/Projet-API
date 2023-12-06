package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Reservation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class ReservationRepository implements PanacheRepositoryBase<Reservation, Long>
{
    public List<Reservation> findByFlights(String flightNumber) {
        return find("flight_id.number", flightNumber).list();
    }

    @Override
    public void persistAndFlush(Reservation reservation) {
        PanacheRepositoryBase.super.persistAndFlush(reservation);
    }
}
