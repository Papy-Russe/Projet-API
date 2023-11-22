package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Passenger;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.List;

public class PassengerRepository implements PanacheRepositoryBase
{
    public List<Passenger> findByOperator(String operatorParameter) {
        return find("operator", operatorParameter).list();
    }
}
