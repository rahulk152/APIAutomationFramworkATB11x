package com.apiautomationframework.base;

import com.apiautomationframework.asserts.AssertActions;
import com.apiautomationframework.endpoints.APIConstants;
import com.apiautomationframework.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.List;

public class BaseTest {
    // CommonToAll Testcase
    //   // Base URL, Content Type - json - common

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest
    public void setup() {
        System.out.println("Starting of the Test");
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

//        requestSpecification = RestAssured.given();
//        requestSpecification.baseUri(APIConstants.BASE_URL);
//        requestSpecification.contentType(ContentType.JSON).log().all();


        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build().log().all();


    }

    public  String getToken(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);
        // Setting the payload
        String payload = payloadManager.setAuthPayload();
        // Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();
        String token = payloadManager.getTokenFromJSON(response.asString());
        return token;

    }

    public Integer getBookingId(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = requestSpecification.contentType(ContentType.JSON).when().get();
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Integer bookingId = bookingIds.get(0);
        return bookingId;
    }


    @AfterTest
    public void tearDown() {
        System.out.println("Finished the Test!");
    }


}
