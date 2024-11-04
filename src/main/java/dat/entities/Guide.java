package dat.entities;

import io.micrometer.common.KeyValues;
import jakarta.persistence.*;
import lombok.*;
import dat.dtos.GuideDTO;
import dat.entities.Trip;
import dat.dtos.TripDTO;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="guide")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "guide", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Trip> trips = new HashSet<>();

    public Guide(GuideDTO guideDTO) {
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.trips = guideDTO.getTrips().stream()
                .map(Trip::new)
                .collect(Collectors.toSet());
    }
}
