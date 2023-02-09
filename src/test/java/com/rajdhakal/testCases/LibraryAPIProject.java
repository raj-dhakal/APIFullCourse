package com.rajdhakal.testCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rajdhakal.files.PayLoads;
import com.rajdhakal.files.ReuseableMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class LibraryAPIProject {
	
	@Test(dataProvider ="BooksData")
	public void addBook(String aisle, String isbm ) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String res = given().log().all().header("Content-Type","application/json").body(PayLoads.addBook(aisle,isbm))
		.when().post("Library/Addbook.php")
		.then().log().all().extract().response().asString();
		JsonPath jp = ReuseableMethods.rawToJson(res);
		String id = jp.get("ID");
		System.out.println(id);
	}
	 
	
	
	@DataProvider(name="BooksData")
	public Object[][] getdata() {
		return new Object[][] {{"mkxtsga1","xhsgata1"},{"kjdhdgsa1","kjhdgsa1"},{"jahsgda1","hdhdgja1"}};
	}
	
	
	
	

}
