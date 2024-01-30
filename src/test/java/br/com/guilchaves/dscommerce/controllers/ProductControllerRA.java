package br.com.guilchaves.dscommerce.controllers;

import br.com.guilchaves.dscommerce.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductControllerRA {

    private String clientUsername, clientPassword;
    private String adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;
    private Long existingProductId, nonExistingProductId, dependentId;
    private String productName;

    private Map<String, Object> postProductInstance;

    @BeforeEach
    public void setUp() throws Exception {
        baseURI = "http://localhost:8080";

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        nonExistingProductId = 0L;
        dependentId = 1L;
        existingProductId = 2L;

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        productName = "Macbook";
        postProductInstance = new HashMap<>();
        postProductInstance.put("name", "The Silmarillion");
        postProductInstance.put("description", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        postProductInstance.put("price", 110.5);
        postProductInstance.put("imgUrl", "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg");

        List<Map<String, Object>> categories = new ArrayList<>();

        Map<String, Object> category1 = new HashMap<>();
        category1.put("id", 2);
        Map<String, Object> category2 = new HashMap<>();
        category2.put("id", 3);

        categories.add(category1);
        categories.add(category2);

        postProductInstance.put("categories", categories);
    }


    @Test
    public void findAllShouldReturnProductWhenIdExists() {
        existingProductId = 1L;
        given().get("products/{id}", existingProductId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", is(Integer.parseInt(String.valueOf(existingProductId))))
                .body("name", equalTo("The Lord of the Rings"))
                .body("price", equalTo(90.5F));
    }

    @Test
    public void findAllShouldReturnPagedProductsWhenProductNameIsEmpty() {
        given().get("products?page=0")
                .then()
                .statusCode(200)
                .body("content.name", hasItems("Macbook Pro", "PC Gamer Tera"));
    }

    @Test
    public void findAllShouldReturnPagedProductsWhenProductNameNotEmpty() {
        given().get("products?name={name}", productName)
                .then()
                .statusCode(200)
                .assertThat()
                .body("content.id[0]", is(3))
                .body("content.name[0]", equalTo("Macbook Pro"))
                .body("content.price[0]", is(1250.0F))
                .body("content.imgUrl[0]", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
    }

    @Test
    public void findAllShouldReturnPagedProductsWithPriceGreaterThan2000() {
        given().get("products?size=25")
                .then()
                .statusCode(200)
                .body("content.findAll { it.price > 2000}.name", hasItems("Smart TV", "PC Gamer Weed"));
    }

    @Test
    public void insertShouldReturnProductCreatedWhenAdminLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("name", equalTo("The Silmarillion"))
                .body("price", is(110.5F))
                .body("imgUrl", equalTo("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
//                .body("categories.id", hasItems(2, 3));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductNameInvalidAndAdminLogged() {
        postProductInstance.remove("name");
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductDescriptionInvalidAndAdminLogged() {
        postProductInstance.remove("description");
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductPriceIsNegativeAndAdminLogged() {
        postProductInstance.put("price", -10.0);
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductPriceIsZeroAndAdminLogged() {
        postProductInstance.put("price", 0.0);
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductHasNotCategoriesAndAdminLogged() {
        postProductInstance.remove("categories");
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422);
    }

    @Test
    public void insertShouldReturnForbiddenAccessWhenClientLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertShouldReturnUnauthorizedWhenNotLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(401);
    }

    @Test
    public void deleteShouldDoNothingWhenProductExistsAndAdminLogged() {
        existingProductId = 10L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/{id}", existingProductId)
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteShouldReturnResourceNotFoundWhenProductDoesNotExistsAndAdminLogged() {
        nonExistingProductId = 0L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/{id}", nonExistingProductId)
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteShouldReturnResourceNotFoundWhenDependentIdAndAdminLogged() {
        dependentId = 1L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/{id}", dependentId)
                .then()
                .statusCode(400);
    }

    @Test
    public void deleteShouldReturnForbiddenAccessWhenClientLogged() {
        existingProductId = 25L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/{id}", existingProductId)
                .then()
                .statusCode(403);
    }


    @Test
    public void deleteShouldReturnUnauthorizedWhenNotLogged() {
        existingProductId = 25L;
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/{id}", existingProductId)
                .then()
                .statusCode(401);
    }
}