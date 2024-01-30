package br.com.guilchaves.dscommerce.controllers;

import br.com.guilchaves.dscommerce.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OrderControllerRA {

    private String adminUsername, adminPassword;
    private String clientUsername, clientPassword;
    private String adminToken, clientToken, invalidToken;
    private Long nonExistingId, existingId, forbiddenId;

    @BeforeEach
    void setUp() throws Exception {
        baseURI = "http://localhost:8080";

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        nonExistingId = 0L;
        existingId = 1L;
        forbiddenId = 2L;

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }

    @Test
    public void findByIdShouldReturnOrderWhenIdExistsAdminLogger() {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", existingId)
                .then()
                .statusCode(200)
                .body("id", is(Integer.parseInt(String.valueOf(existingId))))
                .body("client.id", is(1))
                .body("client.name", is("Maria Brown"));
    }

    @Test
    public void findByIdShouldReturnOrderWhenOrderSelfOwnedAndSelfClientLogged() {
        existingId = 3L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", existingId)
                .then()
                .statusCode(200)
                .body("id", is(Integer.parseInt(String.valueOf(existingId))))
                .body("client.id", is(1))
                .body("client.name", equalTo("Maria Brown"))
                .body("status", equalTo("WAITING_PAYMENT"))
                .body("items[0].name", equalTo("The Lord of the Rings"));
    }

    @Test
    public void findByIdShouldReturnForbiddenWhenOrderNotSelfOwnedAndClientLogged() {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", forbiddenId)
                .then()
                .statusCode(403);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundWhenIdDoesNotExistsAndAdminLogged() {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundWhenIdDoesNotExistsAndClientLogged() {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", nonExistingId)
                .then()
                .statusCode(404);
    }


    @Test
    public void findByIdShouldReturnUnauthorizedWhenNotLogged() {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .accept(ContentType.JSON)
                .when()
                .get("/orders/{id}", existingId)
                .then()
                .statusCode(401);
    }
}