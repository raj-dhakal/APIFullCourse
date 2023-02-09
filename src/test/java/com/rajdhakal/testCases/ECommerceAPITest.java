package com.rajdhakal.testCases;

import org.testng.annotations.Test;

import com.rajdhakal.pojo.LoginPOJO;
import com.rajdhakal.pojo.LoginResponse;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
	
	@Test
	public void token() {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		LoginPOJO lp = new LoginPOJO();
		lp.setUserEmail("swiss_raj_boyz@yahoo.com");
		lp.setUserPassword("YoubaRaj1988");
		RequestSpecification reqLogin = given().log().all().spec(req).body(lp);
		LoginResponse loginResponse = reqLogin.when().post("/client").then().log().all().extract().response().as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		
	}

}
