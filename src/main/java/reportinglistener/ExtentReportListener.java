package reportinglistener;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ExtentReportListener extends TestListenerAdapter {
    private ExtentReports extentReports;
    private ExtentTest extentTest;

    @Override
    public void onStart(ITestContext testContext) {
        ExtentReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("Test Environment", "Dev");
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Java Runtime Version", System.getProperty("java.runtime.version"));
        extentReports.setSystemInfo("Java Specification Version", System.getProperty("java.specification.version"));
        extentReports.setSystemInfo("Java Class Version", System.getProperty("java.class.version"));
        extentReports.setSystemInfo("Operating System Name", System.getProperty("os.name"));
        extentReports.setSystemInfo("Operating System Version", System.getProperty("os.version"));
        extentReports.setSystemInfo("Operating System Architecture", System.getProperty("os.arch"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("User Directory", System.getProperty("user.dir"));
        extentReports.setSystemInfo("User Country", System.getProperty("user.country"));
        extentReports.setSystemInfo("User Language", System.getProperty("user.language"));
        extentReports.setSystemInfo("User Timezone", System.getProperty("user.timezone"));
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.log(Status.FAIL, "Test failed");
        extentTest.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, "Test skipped");
    }
}
