package fr.unilasalle.flight.api.beans;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flights")
public class Vol extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "flights_sequence_inJavaCode", sequenceName ="flights_sequence_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_sequence_inJavaCode")
    private long id;//clé primaire
    @NotBlank(message = "Number cannot be null")
    @Column(nullable = false)
    private String number;
    @NotBlank(message = "Origin cannot be null")
    @Column(nullable = false)
    private String origin;
    @NotBlank(message = "Destination cannot be null")
    @Column(nullable = false)
    private String destination;
    @NotNull(message = "Departure date cannot be null")//décroissant SQL
    @Column(nullable = false)
    private LocalDate departure_date;
    @NotNull(message = "Departure time cannot be null")//décroissant SQL
    @Column(nullable = false)
    private LocalTime departure_time;
    @NotNull(message = "Arrival date cannot be null")//décroissant SQL
    @Column(nullable = false)
    private LocalDate arrival_date;
    @NotNull(message = "Arrival time cannot be null")//décroissant SQL
    @Column(nullable = false)
    private LocalTime arrival_time;

    @NotBlank(message = "Plane Id cannot be null")
    @ManyToOne//plusieurs vols peuvent être associés à un seul avion
    @JoinColumn(name = "plane_id", referencedColumnName = "id")//jointure entre les 2 colonnes (paramétrage de la clé étrangère)
    private Avion plane_id;
}
