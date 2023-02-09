package com.rajdhakal.testCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import com.rajdhakal.files.PayLoads;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BasicsAPITest {

	public static void main(String[] args) {
		//check if Adding Place in server working
		//POST
		 RestAssured.baseURI="https://rahulshettyacademy.com";
		 String postResponse =given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(PayLoads.AddPlace()).when().post("/maps/api/place/add/json")
					.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)")
					.extract().response().asString();
		//System.out.println(postResponse);
		//here we are extracting the specific information for further validation using JsonPath class
		JsonPath js = new JsonPath(postResponse);
		String status = js.getString("status");
		String placeID = js.getString("place_id");
		String scope = js.get("status");
		
		System.out.println("Status: " + status);
		System.out.println("Place ID: " + placeID);
		System.out.println("Scope: " + scope);
		
		System.out.println("****************END POST MEthods*********************");
		
		//Updating new place
		//PUT
		String newAddress = "Chitwan, Nepal";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		System.out.println("*****************End PUT Methods*********************");
		
		
		//checking weather the update field is correct or not
		//GET
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.get("address");
		Assert.assertEquals(actualAddress, newAddress);
		
	
		
	
		
				
	}

}
