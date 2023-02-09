package com.rajdhakal.testCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class PracticeSession {
	
	@Test
	public void addPlace() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Add place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n" + 
				"  \"location\": {\r\n" + 
				"    \"lat\": -38.383494,\r\n" + 
				"    \"lng\": 33.427362\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Frontline house\",\r\n" + 
				"  \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
				"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoe park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"http://google.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}")
		.when().post("/maps/api/place/add/json")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println("Place id is: " +placeID);
		System.out.println("---------------------------------------");
		
		//Get place
		String str = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().post("/maps/api/place/get/json")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(str);
		System.out.println("This Address is : " + js1.getString("address"));
		
		//update place
		System.out.println("-----------put-------------------------------");
		
		String putSTR = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeID).header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\"8b785af4645c9d0bb2b02023f40cf955\",\r\n" + 
				"\"address\":\"Sammamish, USA\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().extract().asString();
		JsonPath putJS = new JsonPath(putSTR);
		System.out.println("Message out put for update place is:  " + putJS.getString("msg"));
		
		
		//Deleting place
		/*System.out.println("---------------Delete Place--------------");
		String deleteSTR = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n" + 
				"    \"place_id\":\""+placeID+"\r\n" + 
				"}")
		.when().delete("/maps/api/place/delete/json")
		.then().log().all().extract().asString();
		JsonPath deleteJS = new JsonPath(deleteSTR);
		System.out.println("Delete place: " + deleteJS.getString("status"));
		*/
		
		
	}

}
