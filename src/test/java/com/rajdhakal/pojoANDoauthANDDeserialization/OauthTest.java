package com.rajdhakal.pojoANDoauthANDDeserialization;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OauthTest {

	
	@Test
	public void oauthPractice() throws InterruptedException {
		
		/*WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth/identifier?scope=https%3A%2F%2Fwww.googleapis"
				+ ".com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id"
				+ "=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri"
				+ "=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verify&service=lso&o2v=2&flowName=GeneralOAuthFlow");
		driver.findElement(By.id("identifierId")).sendKeys("theswissraj");
		driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		driver.findElement(By.name("password")).sendKeys("YoubaRaj1988");
		driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String getCodeURL = driver.getCurrentUrl();
		*/
		String getCodeURL = "https://rahulshettyacademy.com/getCourse.php?state=verify&code=4%2F0AWtgzh60maO_69d61UuIHDmFJ-cAemacReyRCLh0e6nIpfICzQ_qsFyoQT2uaslrGFHuhg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String codeURL = getCodeURL.split("code=")[1];
		String OTC = codeURL.split("&scope")[0];
		
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		
		
		
		
		String accessTokenResponse = given().urlEncodingEnabled(false).log().all().queryParams("code",OTC)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		
		
		
		
		GetCourse gc = given().queryParam("access_token",accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		System.out.println("Linked is : " + gc.getLinkedIn());
	}
	
	
}
