package groupktservice.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;



public class ConfigGenerator extends ConfigDetails {
	
	
	public static void main(String[] args) {

		initializeConfigVariables(args[0],args[1]);
		writeConfig();

	}
	
	public static void initializeConfigVariables(String environment, String urlstring) {


		if (!environment.equalsIgnoreCase("${environment}")){
			env = environment.trim();
		}	
		baseUrl = urlstring;

		System.out.println("Environment is  = " + env);
		System.out.println("urlstr = " + baseUrl);

	}
	public static  void writeConfig(){
		try {
			String directory = System.getProperty("user.dir");
			File configfile = new File(directory+"/Config.properties");
			FileWriter fileWritter = new FileWriter(configfile, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.newLine();
			bufferWritter.append("baseUrl=" + baseUrl);
			bufferWritter.newLine();
			bufferWritter.append("Environment=" + env);
			bufferWritter.newLine();
			bufferWritter.close();
			fileWritter.close();


		} catch (Exception e) {
			System.out.println("Failed to Generate Config.Properties!!");
			e.printStackTrace();
		}
	}

}
