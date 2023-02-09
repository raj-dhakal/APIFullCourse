package com.rajdhakal.oauth;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class ClientCredentialAuthorization {
	
	@Test
	public void clientCredentials() {
	String accessTokenResponse = given().log().all()
			 .queryParams("grant_type","client_credentials")
			 .queryParams("access_token_URL","")
			 
			 .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			 .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			 .queryParams("scope","")
			 .queryParams("client_authentication","")
			 .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
			
			JsonPath js = new JsonPath(accessTokenResponse);
			String accessToken = js.getString("access_token");
			
			
			
			
			
			String response = given().log().all().queryParam("access_token",accessToken)
			.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
			System.out.println(response);
	}

}
