package groupktservice.core;

import io.restassured.RestAssured;

public class UrlProvider extends ConfigDetails {

	public static void Load_Base_Url() {
		if(env.equalsIgnoreCase("test")){
			RestAssured.baseURI=baseUrl;
		}
		else if(env.equalsIgnoreCase("live")){
			RestAssured.baseURI="http://services.groupkt.com";
		}
	}
}

