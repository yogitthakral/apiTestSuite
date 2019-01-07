package groupktservice.resources;

public class EndPoints {

	public static String getAllCountriesEndPoint() {
		String endPoint = "/country/get/all";
		return endPoint;
	}

	public static String getspecificCountryEndPoint(String countryISO2Code) {
		String endPoint = "/country/get/iso2code/"+countryISO2Code;
		return endPoint;
	}
	
	public static String getThePostRequestEndPoint() {
		String endPoint="/country/add";
		return endPoint;
	}
	
}
