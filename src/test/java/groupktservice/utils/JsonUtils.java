package groupktservice.utils;


import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


public class JsonUtils {

	public static JSONObject fileToJsonString(String filePath) {
		try {
            InputStream is = new FileInputStream(filePath);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            return  new JSONObject(jsonTxt);      }
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
}
}

