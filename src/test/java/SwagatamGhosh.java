import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.common.GoodJob;
import utils.Browser;

import java.util.List;
import java.util.stream.Collectors;

import static utils.BrowserWebDriver.openUrl;

public class SwagatamGhosh {
    WebDriver driver;

    @BeforeSuite
    void initObstaclePage() {
        driver = openUrl(Browser.FIREFOX, "https://obstaclecourse.tricentis.com/Account/Login");
    }

    @Test(testName = "The Last Row")
    void TheLastRow() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/70310");
        WebElement orderTable = driver.findElement(By.xpath("//table[@id='orderTable']"));
        List<WebElement> noOfRows = orderTable.findElements(By.xpath(".//tr"));
        WebElement lastRow = noOfRows.get(noOfRows.size() - 1);
        String value = lastRow.findElement(By.xpath(".//td[2]")).getText();
        driver.findElement(By.id("ordervalue")).sendKeys(value);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "The Obvious")
    void TheObvious(){
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73588");
        driver.findElement(By.xpath("//a[text()='Generate Random Text']")).click();
        String randomText = driver.findElement(By.id("randomtext")).getAttribute("value");
        Select dropDown = new Select(driver.findElement(By.id("selectlink")));
        dropDown.selectByVisibleText(randomText);
        driver.findElement(By.id("submitanswer")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        driver.quit();
    }
}

