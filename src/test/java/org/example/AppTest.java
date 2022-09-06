package org.example;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;

public class AppTest {
    @Test
    public void gotAllUserTest(){
        RequestSpecification requestSpecification = RestAssured.given();
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);


        Response response = requestSpecification.expect().statusCode(SC_OK)
                .when().get("https://gorest.co.in/public/v2/users");
        List<User> users =response.jsonPath().getList("", User.class);
        Assert.assertEquals(users.size(),10, "Expected size is ");


    }


}
