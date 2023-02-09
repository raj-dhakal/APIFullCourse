package com.rajdhakal.testCases;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import com.rajdhakal.files.PayLoads;
import com.rajdhakal.files.ReuseableMethods;

public class RestAssuredDemo {

	public static void main(String[] args) {
		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(PayLoads.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = ReuseableMethods.rawToJson(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		//pudating the place
		String address = "Sammaish Washington";
		given().log().all().queryParam("key", "qackick123").header("Content-Type","application/json").body("{\r\n" + 
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+address+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get the current place 
		
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("/maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(getResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		
		
		
				
		
		

	}

}
