package groupktservice.apiTestSuite;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import groupktservice.core.ConfigDetails;
import groupktservice.core.UrlProvider;
import groupktservice.resources.EndPoints;
import groupktservice.utils.JsonUtils;

public class ApiTest extends ConfigDetails {

String log4jConfPath =System.getProperty("user.dir")+"/log4j.properties";
	
	public static Logger log4j ;
	
	@BeforeSuite
	public void initialize_ConfigVariables(){
		System.out.println("the path for log4j is"+log4jConfPath);
		PropertyConfigurator.configure(log4jConfPath);
		log4j = Logger.getLogger("devpinoyLogger");
		try {
			FileInputStream input = new FileInputStream("Config.properties");
			Properties	prop = new Properties();
			prop.load(input);

			
			env = prop.getProperty("Environment");
			log4j.debug(env);

			baseUrl = prop.getProperty("baseUrl");
			log4j.debug(baseUrl);

		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	@BeforeMethod
	public void getBaseConfig() {
		UrlProvider.Load_Base_Url();
	}

	@Test
	public void TC_001_VerifyAllContryResponseHas_US_DE_GB_InAlpha2() {
		get(EndPoints.getAllCountriesEndPoint()).then().assertThat().statusCode(200).and()
		.body("RestResponse.result.alpha2_code", hasItems("US", "GB", "DE")).and()
		.body("RestResponse.result.alpha3_code", hasItems("USA", "GBR", "DEU"));

	}

	@Test
	public void TC_002_VerifyIndividualResponse_US_InAlpha2AndAlpha3() {
		get(EndPoints.getspecificCountryEndPoint("US")).then().assertThat().statusCode(200).and()
		.body("RestResponse.result.alpha2_code", Matchers.equalTo("US")).and()
		.body("RestResponse.result.alpha3_code", Matchers.equalTo("USA")).and()
		.body("RestResponse.result.name", Matchers.equalTo("United States of America"));

	}
	
	@Test
	public void TC_003_VerifyIndividualResponse_US_lowercase_InAlpha2AndAlpha3() {
		get(EndPoints.getspecificCountryEndPoint("us")).then().assertThat().statusCode(200).and()
		.body("RestResponse.result.alpha2_code", Matchers.not(Matchers.equalTo("US"))).and()
		.body("RestResponse.result.alpha3_code", Matchers.not(Matchers.equalTo("USA"))).and()
		.body("RestResponse.result.name", Matchers.not(Matchers.equalTo("United States of America")));
	}

	@Test
	public void TC_004_VerifyIndividualResponse_DE_InAlpha2AndAlpha3() {
		get(EndPoints.getspecificCountryEndPoint("DE")).then().assertThat().statusCode(200).and()
		.body("RestResponse.result.alpha2_code", Matchers.equalTo("DE")).and()
		.body("RestResponse.result.alpha3_code", Matchers.equalTo("DEU")).and()
		.body("RestResponse.result.name", Matchers.equalTo("Germany"));

	}

	@Test
	public void TC_005_VerifyIndividualResponse_GB_InAlpha2AndAlpha3() {
		get(EndPoints.getspecificCountryEndPoint("GB")).then().assertThat().statusCode(200).and()
		.body("RestResponse.result.alpha2_code", Matchers.equalTo("GB")).and()
		.body("RestResponse.result.alpha3_code", Matchers.equalTo("GBR")).and()
		.body("RestResponse.result.name",Matchers.equalTo("United Kingdom of Great Britain and Northern Ireland"));

	}

	@Test
	public void TC_006_VerifyIndividualResponseForNonExistentCountry_AlienCountry() {
		get(EndPoints.getspecificCountryEndPoint("AC")).then().assertThat().statusCode(200).and()
		.body("RestResponse.result", Matchers.equalTo(null)).body("RestResponse.messages[0]",
				Matchers.equalTo("No matching country found for requested code [AC]."));
	}

	@Test
	public void TC_007_VerifyPostRequestThatIsUnderDevelopment()  {
		JSONObject jsonObj=JsonUtils.fileToJsonString(System.getProperty("user.dir")+"/src/test/java/groupktservice/payload/postMethod.json");
		given().contentType("application/json") 
		.body(jsonObj.toString())
		.when().post(EndPoints.getThePostRequestEndPoint()).then().assertThat().statusCode(201);
	}

}
