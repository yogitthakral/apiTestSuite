package groupktservice.CustomReporter;

import java.util.Properties;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
	public static Properties prop = new Properties();

	public static ExtentReports extent;
	public static String filePath = System.getProperty("user.dir") + "/finalReport/TestExecutionReport.html";
	


	public synchronized static ExtentReports getExtent()  {
		
		try{
		if (extent == null) {
			extent = new ExtentReports();
			
			extent.attachReporter(getHtmlReporter());
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.config().enableTimeline(false);
		
		// report title
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("HelloFresh Automation Test's Report");
		return htmlReporter;
	}

	

}
