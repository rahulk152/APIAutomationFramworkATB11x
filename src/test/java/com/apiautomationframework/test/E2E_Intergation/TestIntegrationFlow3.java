package com.apiautomationframework.test.E2E_Intergation;

import com.apiautomationframework.base.BaseTest;
import com.apiautomationframework.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

public class TestIntegrationFlow3 extends BaseTest {

    // TestE2EFlow_03

    //  Test E2E Scenario 3  -> Try to Delete that booking

    //  1. Get a Booking from Get All -> bookingID

    // 2. Delete the Booking - Need to get the token, bookingID from above request

    @Test(groups = "qa", priority = 1)
    @Owner("Rahul")
    @Description("TC#INT1 - Step 1. Verify that the Booking By ID")
    public void testVerifyGetAllBookingId(ITestContext iTestContext) {

        Integer bookingid = getBookingId();
        iTestContext.setAttribute("bookingid", bookingid);
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Rahul")
    @Description("TC#INT1 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);
        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }

}
