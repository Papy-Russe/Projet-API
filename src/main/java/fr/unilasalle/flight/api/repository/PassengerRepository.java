package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Passenger;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class PassengerRepository implements PanacheRepositoryBase<Passenger, Long>
{
    public List<Passenger> findByOperator(String operatorParameter) {
        return find("operator", operatorParameter).list();
    }
}
