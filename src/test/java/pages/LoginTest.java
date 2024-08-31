package pages;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.login.LoginPage;
import utils.Browser;
import utils.Credentials;

public class LoginTest {
    WebDriver driver;

    @BeforeSuite
    void initDriver() {
        driver = new Browser().openBrowser("edge");
    }

    @Test
    void loginTestObstacle() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Credentials.getUserName(), Credentials.getPassword());
    }

    @AfterSuite
    void tearDownSession() {
        driver.quit();
    }
}
