package fr.unilasalle.flight.api;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "planes")
public class Avion extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "planes_sequence_inJavaCode", sequenceName ="planes_sequence_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_sequence_inJavaCode")
    private long id;//clé primaire
    @Column(nullable = false)
    private String operator;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false, unique = true)
    private String immatriculation;
    @Column(nullable = false)
    private int capacity;
}
