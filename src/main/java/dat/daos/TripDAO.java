package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {
    private static EntityManagerFactory emf;

    private static TripDAO instance;

    public TripDAO(EntityManagerFactory emf) {
        TripDAO.emf = emf;
    }

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new TripDAO(_emf);
        }
        return instance;
    }

    @Override
    public TripDTO getByID(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new EntityNotFoundException("No trip found with ID: " + id);
            }
            return new TripDTO(trip);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<TripDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> tripList = query.getResultList();
            return tripList.stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toSet());
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();


            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();

            return new TripDTO(trip);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripDTO.getId());
            if (trip == null) {
                throw new EntityNotFoundException("No trip found with ID: " + tripDTO.getId());
            }
            em.remove(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new EntityNotFoundException("No trip found with ID: " + id);
            }
            em.remove(trip);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO update(TripDTO tripDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Find the existing Trip entity
            Trip existingTrip = em.find(Trip.class, tripDTO.getId());
            if (existingTrip == null) {
                throw new EntityNotFoundException("No trip found with ID: " + tripDTO.getId());
            }

            // Update the fields
            existingTrip.setName(tripDTO.getName());
            existingTrip.setStartTime(tripDTO.getStartTime());
            existingTrip.setEndTime(tripDTO.getEndTime());
            existingTrip.setStartPosition(tripDTO.getStartPosition());
            existingTrip.setPrice(tripDTO.getPrice());
            existingTrip.setCategory(tripDTO.getCategory());

            // Persist the changes
            em.merge(existingTrip);
            em.getTransaction().commit();

            return new TripDTO(existingTrip);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);

            if (trip == null) {
                throw new EntityNotFoundException("Trip not found with ID: " + tripId);
            }
            if (guide == null) {
                throw new EntityNotFoundException("Guide not found with ID: " + guideId);
            }

            trip.setGuide(guide);
            guide.getTrips().add(trip);

            em.merge(trip);
            em.merge(guide);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }


    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        EntityManager em = emf.createEntityManager();
        try {
            Guide guide = em.find(Guide.class, guideId);
            if (guide == null) {
                throw new EntityNotFoundException("Guide not found with ID: " + guideId);
            }

            return guide.getTrips().stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toSet());
        } finally {
            em.close();
        }
    }

    public boolean validatePrimaryKey(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Trip trip = em.find(Trip.class, id);
            return trip != null;
        } finally {
            em.close();
        }
    }
}
