package dat.config;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.Category;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Populate {

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tripplanning");
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Guide guide1 = new Guide();
            guide1.setFirstName("Alice");
            guide1.setLastName("Smith");
            guide1.setEmail("alice.smith@example.com");
            guide1.setPhone("123-456-7890");
            guide1.setTrips(new HashSet<>());

            Guide guide2 = new Guide();
            guide2.setFirstName("Bob");
            guide2.setLastName("Johnson");
            guide2.setEmail("bob.johnson@example.com");
            guide2.setPhone("234-567-8901");
            guide2.setTrips(new HashSet<>());

            Guide guide3 = new Guide();
            guide3.setFirstName("Clara");
            guide3.setLastName("Lee");
            guide3.setEmail("clara.lee@example.com");
            guide3.setPhone("345-678-9012");
            guide3.setTrips(new HashSet<>());

            em.persist(guide1);
            em.persist(guide2);
            em.persist(guide3);

            Trip trip1 = new Trip();
            trip1.setName("Guided Tour of the City");
            trip1.setStartTime(LocalDate.of(2024, 1, 15));
            trip1.setEndTime(LocalDate.of(2024, 1, 16));
            trip1.setStartPosition("Central Park");
            trip1.setPrice(100);
            trip1.setCategory(Category.CITY);
            trip1.setGuide(guide1);
            guide1.getTrips().add(trip1);

            Trip trip2 = new Trip();
            trip2.setName("Mountain Hiking Adventure");
            trip2.setStartTime(LocalDate.of(2024, 2, 20));
            trip2.setEndTime(LocalDate.of(2024, 2, 21));
            trip2.setStartPosition("Mountain Base Camp");
            trip2.setPrice(150);
            trip2.setCategory(Category.FOREST);
            trip2.setGuide(guide2);
            guide2.getTrips().add(trip2);

            Trip trip3 = new Trip();
            trip3.setName("Bali Beach Vacation");
            trip3.setStartTime(LocalDate.of(2024, 3, 10));
            trip3.setEndTime(LocalDate.of(2024, 3, 12));
            trip3.setStartPosition("Beach Resort");
            trip3.setPrice(200);
            trip3.setCategory(Category.BEACH);
            trip3.setGuide(guide3);
            guide3.getTrips().add(trip3);

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);

            em.getTransaction().commit();
        }
    }
}
