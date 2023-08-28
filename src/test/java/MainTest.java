import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.common.GoodJob;
import pages.login.LoginPage;
import pages.obstacles.NotATable;
import utils.Browser;
import utils.Credentials;

import static utils.BrowserWebDriver.openUrl;

public class MainTest {
    WebDriver driver;

    @BeforeSuite
    void initObstaclePage() {
        driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Account/Login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Credentials.getUserName(), Credentials.getPassword());
    }

    @Test(testName = "Not a table")
    void NotATable() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/64161");
        NotATable notATable = new NotATable(driver);
        notATable.clickGenerateOrderId();
        String orderId = notATable.getOrderID();
        notATable.enterOrderId(orderId);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        driver.quit();
    }
}
