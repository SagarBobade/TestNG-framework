package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageObjects.PageObjects;

public class CommonMethods {

public static int hitUserAuthAPI(String userAuthAPIUrl, String display, String username, String password) {
		
	System.out.println("dfdfdfd");
		RestAssured.baseURI = userAuthAPIUrl;
		RequestSpecification reqSpec = RestAssured.given();
		String bearerToken=null;
		
		reqSpec.header("Content-Type", "application/x-www-form-urlencoded");
		reqSpec.formParam("display", display);
		reqSpec.formParam("grant_type", "implicit");
		reqSpec.formParam("password", password);
		reqSpec.formParam("username", username);

		try {
			Response resp = reqSpec.request().post();
			bearerToken =  resp.getBody().jsonPath().get("access_token");
			if(bearerToken!=null) {
				PageObjects.tokenValue = resp.getBody().jsonPath().get("access_token");
				System.out.println(resp.getStatusCode());
				return resp.getStatusCode();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("response fail");
		}
		return 0;
	}
}
