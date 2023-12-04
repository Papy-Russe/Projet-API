package fr.unilasalle.flight.api.beans;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passengers")
public class Passenger extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "passengers_sequence_inJavaCode", sequenceName ="passengers_sequence_database", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passengers_sequence_inJavaCode")
    private long id;//clé primaire
    @NotBlank(message = "Surname cannot be null")
    @Column(nullable = false)
    private String surname;
    @NotBlank(message = "First name cannot be null")
    @Column(nullable = false)
    private String firstname;
    @NotBlank(message = "E-mail address cannot be null")
    @Column(nullable = false)
    private String emailaddress;
}
