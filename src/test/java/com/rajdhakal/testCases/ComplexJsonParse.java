package com.rajdhakal.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rajdhakal.files.PayLoads;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	
	private JsonPath js = new JsonPath(PayLoads.CoursePrice());
	@Test
	public void printAll() {
		
		int size = js.getInt("courses.size()");
		System.out.println(size);
		
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		for(int i = 0; i < size; i++) {
			System.out.println(js.getString("courses["+i+"].title"));
		}
		

	}
	
	
	@Test
	public void sumAll() {
		
	}

}
