package dat.dtos;

import dat.entities.Trip;
import dat.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Integer id;
    private String name;
    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private Integer price;
    private Category category;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
    }
}
