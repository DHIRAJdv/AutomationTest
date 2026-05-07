package com.krct;

import com.sun.net.httpserver.Request;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class CategoryTest {
    private int id;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void testCreateCategory() {

        String name = "user" + System.currentTimeMillis();
        String image = "https://picsum.photos/200";

        Map<String, Object> body = Map.of(
                "name", name,
                "image", image
        );
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/categories");
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("name", Matchers.equalTo(name))
                .body("image", Matchers.equalTo(image));
        id = response.jsonPath().getInt("id");
    }

    @Test(priority = 2)
    public void testGetCategory() {
        RestAssured.given()
                .pathParam("id", id)
                .when()
                .get("/categories/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", Matchers.equalTo(id));
    }

    @Test(priority = 3)
    private void testUpdateCategory() {
        String name = "category_" +System.currentTimeMillis();
//        String image  = "https://picsum.photos/200";
//        Map<String, Object> body = Map.of(
//                "name", name,
//                "image", image
//        );

        RestAssured.given()
                .pathParam("id", id)
                .formParam("name", name)
                .when()
                .put("/categories/{id}")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(name));
    }





}