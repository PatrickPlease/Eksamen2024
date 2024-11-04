package dat.entities;

import dat.dtos.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="trips")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false)
    private Integer id;

    @Column(name = "starttime", nullable = false)
    private LocalDate startTime;

    @Column(name = "endtime", nullable = false)
    private LocalDate endTime;

    @Column(name = "startposition", nullable = false)
    private String startPosition;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    public Trip(TripDTO tripDTO) {
        this.id = tripDTO.getId();
        this.name = tripDTO.getName();
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.startPosition = tripDTO.getStartPosition();
        this.price = tripDTO.getPrice();
        this.category = Category.valueOf(String.valueOf(tripDTO.getCategory()));
    }
}
