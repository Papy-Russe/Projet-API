package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Reservation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.List;

public class ReservationRepository implements PanacheRepositoryBase<Reservation, Long>
{
    public List<Reservation> findByOperator(String operatorParameter) {
        return find("operator", operatorParameter).list();
    }
}