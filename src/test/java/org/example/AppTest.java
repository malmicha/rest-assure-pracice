package org.example;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class AppTest {
    @Test
    public void gotAllUserTest() {
        RequestSpecification requestSpecification = RestAssured.given();
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);


        Response response = requestSpecification.expect().statusCode(SC_OK)
                .when().get("https://gorest.co.in/public/v2/users");
        List<User> users = response.jsonPath().getList("", User.class);
        Assert.assertEquals(users.size(), 10, "Expected size is ");


    }

    @Test
    public void createUserTest() {
        RequestSpecification requestSpecification =
                RestAssured.given().auth().oauth2("2a619c0a7205958156174142331854120199f85a1efb7d36cb5b3274a5306e92");
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        requestSpecification.headers(headers);

        User expectedUser = createUser();
        Response createdUserResponse = requestSpecification.body(expectedUser).expect().statusCode(SC_CREATED)
                .when().post("https://gorest.co.in/public/v2/users/");
        User actualUser = createdUserResponse.as(User.class);
        Assert.assertEquals(actualUser.getName(), expectedUser.getName(), "Expected user ");
    }

    private User createUser() {
        Random random = new Random();
        User user = new User();
        user.setName("test" + random.nextInt());
        user.setEmail("testEmail" + random.nextInt() + "@gmail.com");
        user.setGender("Male");
        user.setStatus("active");

        return user;


    }
}