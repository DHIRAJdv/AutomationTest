package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class FakeAPITest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testGetProducts() {
        RestAssured.given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.greaterThan(0));
    }

    @Test(priority = 2)
    public void testFilterProductByPrice() {
        RestAssured.given()
                .queryParam("price", "100")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("[0].price", Matchers.equalTo(100));
    }

    @Test(priority = 3)
    public void testGerCategories() {
        RestAssured.given()
                .when()
                .get("/categories")
                .then()
                .statusCode(200)
                .body("$", Matchers.instanceOf(List.class));
    }

    @Test(priority = 4)
    public void testGetCategoriesById() {
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .get("categories/{id}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1));
    }

    @Test(priority = 5)
    public void FilterByPriceRange() {
        RestAssured.given()
                .queryParam("price_min", "900")
                .queryParam("price_max", "1000")
                .when()
                .get("products")
                .then()
                .statusCode(200)
                .body("price", Matchers.everyItem(Matchers.greaterThanOrEqualTo(900)))
                .body("price", Matchers.everyItem(Matchers.lessThanOrEqualTo(1000)));
    }

    @Test(priority = 6)
    public void testCreateCategory() {

        String name = "dj_" + System.currentTimeMillis();

        Map<String, Object> body = Map.of(
                "name", name,
                "image", "https://placeimg.com/640/480/any"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("categories")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name));
    }
}
