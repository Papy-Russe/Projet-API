package fr.unilasalle.flight.api.repository;

import fr.unilasalle.flight.api.beans.Avion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.List;

public class AvionRepository implements PanacheRepositoryBase<Avion, Long>
    {
        public List<Avion> findByOperator(String operatorParameter) {
            return  find("operator", operatorParameter).list();
        }
    }
