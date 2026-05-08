package com.krct;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class AuthTest {
    private String name;
    private String email;
    private String password;
    @BeforeClass
    public void setup()
    {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority=1)
    public void testRegister(){
        name = "user_"+System.currentTimeMillis();
        email = name+"@gmail.com";
        password = "1234567890";
        String avatar = "https://picsum.photos/800";
        Map body = Map.of(
                "name", name,
                "email", email,
                "password", password,
                "avatar", avatar
        );
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", Matchers.notNullValue());
    }
    @Test(priority=2)
    public void testLoginUser(){
        Map body = Map.of(
                "email", email,
                "password", password
        );
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(201)
                .body("access_token", Matchers.notNullValue())
                .body("refresh_token", Matchers.notNullValue());
    }
}
