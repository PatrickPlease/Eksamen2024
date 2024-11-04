package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populate;
import dat.controllers.TripController;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.dtos.TripDTO;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import dat.util.LoginUtil;
import dat.entities.Category;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TripRoutesTest {
    private static Javalin app;
    private static EntityManagerFactory emfTest = HibernateConfig.getEntityManagerFactoryForTest();
    private static int port = 9090;
    private static String adminToken;
    private static String userToken;

    @BeforeAll
    static void setup() {
        app = ApplicationConfig.startServer(port);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        LoginUtil.createTestUsers(emfTest);
        adminToken = LoginUtil.getAdminToken();
        userToken = LoginUtil.getUserToken();
        Populate.populate(emfTest);
    }

    @AfterAll
    static void tearDown() {
        ApplicationConfig.stopServer(app);
    }

/*
    @Test
    public void testCreateTrip() {
        given()
                .header("Authorization", userToken)
                .contentType("application/json")
                .body({"name":"Trip","description":"NY TEST JA TAK"})
                .when()
                .post("/trips")
                .then()
                .statusCode(201);
    }
*/
    @Test
    public void testGetAllTrips() {
        given()
                .header("Authorization", userToken)
                .contentType("application/json")
                .when()
                .get("/api/trips/")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue());
    }

    @Test
    public void testGetTripById() {
        given()
                .header("Authorization", userToken)
                .contentType("application/json")
                .when()
                .get("/api/trips/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    /*
    @Test
    public void testUpdateTrip() {
        given()
                .header("Authorization", adminToken)
                .contentType("application/json")
                .body("{\"name\":\"Updated name\",\"description\":\"Updated description\"}")
                .when()
                .put("/api/authors/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated name"));
    }

     */

    @Test
    public void testDeleteTrip() {
        given()
                .header("Authorization", adminToken)
                .contentType("application/json")
                .when()
                .delete("/api/trips/1")
                .then()
                .statusCode(204);
    }
}
