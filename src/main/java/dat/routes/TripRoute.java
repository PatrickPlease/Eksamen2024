package dat.routes;


import dat.controllers.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoute {

    private final TripController tripController = new TripController();

    // Some of the routes has admin and user roles, its because admin does not work atm
    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", tripController::createTrip, Role.USER, Role.ADMIN);
            get("/", tripController::getAllTrips, Role.ANYONE);
            get("/{id}", tripController::getTripById, Role.ANYONE);
            put("/{id}", tripController::updateTrip, Role.USER, Role.ADMIN);
            delete("/{id}", tripController::deleteTrip, Role.USER, Role.ADMIN);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.USER, Role.ADMIN);
            post("/trips/populate", tripController::populateDatabase, Role.USER, Role.ADMIN);
        };
    }
}



