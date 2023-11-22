package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.List;

public class VolsRepository implements PanacheRepositoryBase<Vol, Long> {
    public List<Vol> findByOperator(String operatorParameter){
        return find("operator", operatorParameter).list();
    }
}
