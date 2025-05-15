package com.apiautomationframework.test.E2E_Intergation;

import com.apiautomationframework.base.BaseTest;
import com.apiautomationframework.endpoints.APIConstants;
import com.apiautomationframework.pojos.request.Booking;
import com.apiautomationframework.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow5 extends BaseTest {

    // TestE2EFlow_01

    //  Test E2E Scenario 1

    //  1. Create Token -> token

    // 2. Delete the Booking - Need to get the token, bookingID from above request

    // 3. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request


    @Test(groups = "qa", priority = 1)
    @Owner("Rahul")
    @Description("TC#INT1 - Step 1. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {

        Integer bookingid = getBookingId();
        iTestContext.setAttribute("bookingid", bookingid);
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }
    @Test(groups = "qa", priority = 2)
    @Owner("Rahul")
    @Description("TC#INT1 - Step 2. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = (String)  iTestContext.getAttribute("token");

        String basepathPUT = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basepathPUT);

        requestSpecification.basePath(basepathPUT);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();

        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(405);

//        Booking booking = payloadManager.getResponseFromJSON(response.asString());
//
//        assertActions.verifyStringKeyNotNull(booking.getFirstname());
//        assertActions.verifyStringKey(booking.getFirstname(), "Deepak");
    }

}
