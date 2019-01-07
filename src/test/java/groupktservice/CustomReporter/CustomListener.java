package groupktservice.CustomReporter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import groupktservice.apiTestSuite.ApiTest;



public class CustomListener implements ITestListener {

	public static ExtentReports extent = ExtentManager.getExtent();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();



	@Override
	public synchronized void onStart(ITestContext context) {
	}



	@Override
	public synchronized void onFinish(ITestContext testContext) {
		extent.flush();
	}


	@Override
	public synchronized void onTestStart(ITestResult result) {

		ExtentTest parent;	
		String testName=result.getMethod().getMethodName();

		parent = extent.createTest(testName);
		String[] category=result.getMethod().getTestClass().getName().split("\\.");
		parent.assignCategory(category[category.length-1]);
		test.set(parent);



	}



	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		test.get().pass(result.getMethod().getMethodName()+" passed");

		try {
			ApiTest.log4j.debug(result.getMethod().getMethodName()+"_PASSED"+"_TimeStamp:"+getDateTimeIP());
		} catch (Exception e) {
			e.printStackTrace();
		}	



	}


	@Override
	public synchronized void onTestFailure(ITestResult result) {


		StringWriter sw = new StringWriter();
		result.getThrowable().printStackTrace(new PrintWriter(sw));

		if(sw.toString().equalsIgnoreCase("null")) {
			sw.write("No Exception was thrown by the test case");
		}

		try{
				if(result.getThrowable()!=null && result.getThrowable().toString().contains("java.lang.Throwable: Multiple failures")){
					test.get().skip(result.getMethod().getMethodName()+" Skipped because of multiple failures.");	

				}else{
						if(result.getThrowable().toString().contains("java.lang.AssertionError")){

							test.get().log(Status.FAIL, "<pre>"+sw.toString()+"</pre>");
						}
						else
						{
							test.get().log(Status.SKIP, "<pre>"+sw.toString()+"</pre>");

						}

					}

				
			
				ApiTest.log4j.debug(result.getMethod().getMethodName()+"_FAILED"+"_TimeStamp:"+getDateTimeIP());
		}
		catch(Exception e){

			test.get().log(Status.SKIP, "<pre>"+"Test Case skipped because of an exception in the custom repoter method, Please find the exception here :- \n"+e+"</pre>");

		}

	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		ExtentTest parent;	
		String testName=result.getMethod().getMethodName();

		try{
				if(test.get()==null ||((!test.get().getModel().getName().contains(result.getMethod().getMethodName()))&& result.getParameters().length==0)){
					parent = extent.createTest(testName);
					String[] category=result.getMethod().getTestClass().getName().split("\\.");
					parent.assignCategory(category[category.length-1]);
					test.set(parent);

				}



			if(result.getThrowable()!=null && result.getThrowable().toString().contains("java.lang.Throwable: Multiple failures")){
				test.get().skip(result.getMethod().getMethodName()+" Skipped because of multiple failures.");	
			}else{
				
					test.get().log(Status.SKIP, "<pre>"+result.getThrowable()+"</pre>");
					test.get().skip("Test Skipped "+result.getMethod().getMethodName() );
				
			}



			ApiTest.log4j.debug(result.getMethod().getMethodName()+"_SKIPPED"+"_TimeStamp:"+getDateTimeIP());

		}catch(Exception e) {
			test.get().log(Status.SKIP, "<pre>"+"Test Case skipped because of an exception in the custom repoter method, Please find the exception here :- \n"+e+"</pre>");


		}


	}






	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public String getDateTimeIP() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ssaa_dd_MMM_yyyy");
		Date date = new Date();
		InetAddress ownIP = InetAddress.getLocalHost();
		return (dateFormat.format(date) + " _IP" + ownIP.getHostAddress());
	}



}


