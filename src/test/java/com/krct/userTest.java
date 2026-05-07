package com.krct;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class userTest {
    private int id;
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.escuelajs.co/api/v1";
    }

    @Test(priority = 1)
    public void 
}
