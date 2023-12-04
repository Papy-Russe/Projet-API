package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class VolsRepository implements PanacheRepositoryBase<Vol, Long> {
    public List<Vol> findByDestination(String operatorParameter){
        return find("destination", operatorParameter).list();
    }
}
