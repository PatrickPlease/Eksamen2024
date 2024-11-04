package dat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import dat.entities.Guide;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Set<TripDTO> trips;

    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.trips = guide.getTrips().stream()
                .map(TripDTO::new)
                .collect(Collectors.toSet());
    }
}
