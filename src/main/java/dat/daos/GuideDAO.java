package dat.daos;

import dat.dtos.GuideDTO;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.Set;
import java.util.stream.Collectors;

public class GuideDAO implements IDAO<GuideDTO> {
    private EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public GuideDTO getByID(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Guide guide = em.find(Guide.class, id);
            if (guide == null) throw new EntityNotFoundException("No guide found with ID: " + id);
            return new GuideDTO(guide);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<GuideDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Guide> query = em.createNamedQuery("Guide.findAll", Guide.class);
            return query.getResultList().stream()
                    .map(GuideDTO::new)
                    .collect(Collectors.toSet());
        } finally {
            em.close();
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);
            em.persist(guide);
            em.getTransaction().commit();
            return new GuideDTO(guide);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public GuideDTO update(GuideDTO guideDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Guide existingGuide = em.find(Guide.class, guideDTO.getId());
            if (existingGuide == null) {
                throw new EntityNotFoundException("No guide found with ID: " + guideDTO.getId());
            }

            existingGuide.setFirstName(guideDTO.getFirstName());
            existingGuide.setLastName(guideDTO.getLastName());
            existingGuide.setEmail(guideDTO.getEmail());
            existingGuide.setPhone(guideDTO.getPhone());

            em.merge(existingGuide);
            em.getTransaction().commit();

            return new GuideDTO(existingGuide);
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
    public void delete(GuideDTO guideDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, guideDTO.getId());
            if (guide == null) throw new EntityNotFoundException("No guide found with ID: " + guideDTO.getId());
            em.remove(guide);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
