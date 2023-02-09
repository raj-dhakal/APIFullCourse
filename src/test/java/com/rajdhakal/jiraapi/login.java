package com.rajdhakal.jiraapi;

import org.testng.annotations.Test;

import com.rajdhakal.files.ReuseableMethods;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class login {
	
	@Test
	public void createIssues() {
		
		RestAssured.baseURI="http://localhost:8080";
		
		
		//login into JIRA website before creating anything 
		SessionFilter session = new SessionFilter();//this will reduce your work for e.g. you do not need to create JsonPath and extract the sessioID to login and all
		String response = given().log().all().header("Content-Type","application/json").body("{ \"username\": \"rajdhakal1988\", \"password\": \"YoubaRaj1988\" }")
				.filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		
		//----------this below part take care by session filter class as we done above
		/*JsonPath js = ReuseableMethods.rawToJson(response); 
		String value = js.getString("value");
		System.out.println(value);*/
		
		given().log().all().pathParam("key", "10003").header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \"My name is Raj and I am commenting for JIRA ResAssured\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session)
			.when().post("/rest/api/2/issue/{key}/comment")
			.then().log().all().assertThat().statusCode(201);
		
		
		//new concept of ADD attachment  in JIRA
		//curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		// D- different parameter
		//u username and password
		//X = http method using
		//-H stands for header
		//-F- the file which you want to attached
		String addCommentResponse = given().relaxedHTTPSValidation().pathParam("key", "10003").header("Content-Type","multipart/form-data").header("X-Atlassian-Token","no-check").filter(session)
		.multiPart("file",new File("jira.txt")) //if your file in project level then just use jira.txt otherwise give full path like: C://...
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentID = js.getString("key");
		
		
		
		//get an issue
		String response1 = given().log().all().pathParam("key", "10003").queryParam("fields", "comment").filter(session)
		.when().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		System.out.println(response1);
		JsonPath js1 = new JsonPath(response1);
		int commentsCount = js1.get("fields.comment.comments.size()");
		for(int i = 0; i < commentsCount; i++) {
			System.out.println(js1.get("fields.comment.comments[" +i+"].id"));
			
			
		}
		
		//Notes:
		//In a real time when you work in a project in server  when you try to authenticate your self with log in mechanisms in 
		//general in HTTPS sometimes restassured may not able to recognize the certificate it generates so to by pass https validation there is 
		// one method that can use to by pass : relaxedHTTPSValidation after given() so it will take care everythings for e.g.
		/*
		 SessionFilter session = new SessionFilter();//this will reduce your work for e.g. you do not need to create JsonPath and extract the sessioID to login and all
		String response = given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json").body("{ \"username\": \"rajdhakal1988\", \"password\": \"YoubaRaj1988\" }")
				.filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		 
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}// createIssues method end
	
	
	
	

}
