package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservations")
public class    Reservation extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "reservations_sequence_inJavaCode", sequenceName ="reservations_sequence_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations_sequence_inJavaCode")
    private long id;//clé primaire
    @NotNull(message = "Flight Id cannot be null")
    @ManyToOne//plusieurs réservations peuvent être associées à un seul vol
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Vol flight_id;
    @NotNull(message = "Passenger Id cannot be null")
    @ManyToOne//plusieurs réservations peuvent être associées à un seul passager
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger_id;
}
