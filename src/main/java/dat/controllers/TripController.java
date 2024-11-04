package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.daos.TripDAO;
import dat.dtos.TripDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;

public class TripController {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tripplanning");
    private TripDAO tripDAO = new TripDAO(emf);

    public TripController() {
        this.tripDAO = tripDAO.getInstance(emf);
    }

    public void createTrip(Context ctx) {
        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
        TripDTO createdTrip = tripDAO.create(tripDTO);
        ctx.res().setStatus(201);
        ctx.json(createdTrip, TripDTO.class);
    }


    public void getAllTrips(Context ctx) {
        Set<TripDTO> trips = tripDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(trips, TripDTO.class);
    }


    public void getTripById(Context ctx) {
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        TripDTO tripDTO = tripDAO.getByID(id);
        if (tripDTO != null) {
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } else {
            ctx.res().setStatus(404);
            ctx.json("Trip ID not found");
        }
    }


    public void updateTrip(Context ctx) {
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
        tripDTO.setId(id);
        TripDTO updatedTrip = tripDAO.update(tripDTO);
        ctx.res().setStatus(200);
        ctx.json(updatedTrip, TripDTO.class);
    }

    public void deleteTrip(Context ctx) {
        Integer id = Integer.valueOf(ctx.pathParam("id"));
        try {
            tripDAO.deleteById(id);
            ctx.res().setStatus(204);
        } catch (EntityNotFoundException e) {
            ctx.res().setStatus(404);
            ctx.json("Trip not found with ID: " + id);
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json("An error occurred while deleting the trip: " + e.getMessage());
        }
    }

    public void populateDatabase(Context ctx) {
        try {
            Populate.populate(emf);
            ctx.res().setStatus(200);
            ctx.json("Database populated successfully.");
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json("Error populating database: " + e.getMessage());
        }
    }

    public void addGuideToTrip(Context ctx) {
        Integer tripId = Integer.valueOf(ctx.pathParam("tripId"));
        Integer guideId = Integer.valueOf(ctx.pathParam("guideId"));

        try {
            tripDAO.addGuideToTrip(tripId, guideId);
            ctx.res().setStatus(200);
            ctx.json("Guide added to trip successfully.");
        } catch (EntityNotFoundException e) {
            ctx.res().setStatus(404);
            ctx.json(e.getMessage());
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json("An error occurred while adding the guide to the trip.");
        }
    }
}
