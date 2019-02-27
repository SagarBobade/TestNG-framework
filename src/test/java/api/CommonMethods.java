package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pageObjects.PageObjects;

public class CommonMethods {

	public static int hitUserAuthAPI(String userAuthAPIUrl, String display, String username, String password) {

		RestAssured.baseURI = userAuthAPIUrl;
		RequestSpecification reqSpec = RestAssured.given();
		String bearerToken = null;

		reqSpec.header("Content-Type", "application/x-www-form-urlencoded");
		reqSpec.formParam("display", display);
		reqSpec.formParam("grant_type", "implicit");
		reqSpec.formParam("password", password);
		reqSpec.formParam("username", username);

		try {
			Response resp = reqSpec.request().post();
			bearerToken = resp.getBody().jsonPath().get("access_token");
			if (bearerToken != null) {
				PageObjects.tokenValue = resp.getBody().jsonPath().get("access_token");
				return resp.getStatusCode();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("response fail");
		}
		return 0;
	}

	public static Response testResponseCode(String reqUrl, String methodName, double expectedCode1, String jsonBody) {

		int expectedCode = Integer.valueOf((int) Math.round(expectedCode1));

		/*
		 * if (!jsonBody.contains("NA")) { JSONObject jsonobj = new
		 * JSONObject(jsonBody); }
		 */
		Response resp = null;
		int code = 0;
		RestAssured.baseURI = reqUrl;
		RequestSpecification request = RestAssured.given();
		if (!(PageObjects.tokenValue == null)) {
			if (!PageObjects.tokenValue.contains("Bearer")) {
				PageObjects.tokenValue = "Bearer " + PageObjects.tokenValue;
			}
			request.header("Content-Type", "application/json");
			request.header("Authorization", PageObjects.tokenValue);
		} else {
			request.header("Content-Type", "application/json");
		}

		if (methodName.equalsIgnoreCase("get")) {
			resp = request.request().get(RestAssured.baseURI);

		} else if (methodName.equalsIgnoreCase("post")) {
			if (!jsonBody.equalsIgnoreCase(null)) {
				request.body(jsonBody);
			}
			resp = request.post();
		} else if (methodName.equalsIgnoreCase("put")) {
			request.body(jsonBody);
			resp = request.put();
		} else if (methodName.equalsIgnoreCase("delete")) {
			resp = request.delete();
		}
		if (resp != null) {
			code = resp.getStatusCode();
		}
		if (code == Math.round(expectedCode)) {
			return resp;
		} else {
			System.out.println("Not matched with expected code");
			return null;
		}

	}
}
