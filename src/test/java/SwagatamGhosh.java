import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
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
        driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Account/Login");
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
    void TheObvious() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73588");
        driver.findElement(By.xpath("//a[text()='Generate Random Text']")).click();
        String randomText = driver.findElement(By.id("randomtext")).getAttribute("value");
        Select dropDown = new Select(driver.findElement(By.id("selectlink")));
        dropDown.selectByVisibleText(randomText);
        driver.findElement(By.id("submitanswer")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Scroll Into View")
    void ScrollIntoView() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/99999");
        WebElement container = driver.findElement(By.id("container"));
        container.sendKeys(Keys.END);
        driver.switchTo().frame("container");
        driver.findElement(By.id("textfield")).sendKeys("Tosca");
        driver.switchTo().defaultContent();
        driver.findElement(By.id("submit")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Bubble Sort")
    void BubbleSort() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73589");

    }

    @Test(testName = "Halfway")
    void Hafway() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41038");
        WebElement clickButton = driver.findElement(By.id("halfButton"));

    }

    @Test(testName = "Meeting Scheduler")
    void MeetingScheduler() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41037");
        WebElement scheduleTable = driver.findElement(By.id("timeTable"));

    }

    @Test(testName = "Fun With Tables")
    void FunWithTables() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/92248");
        WebElement dataTable = driver.findElement(By.id("persons"));

    }

    @Test(testName = "Toscabot Can Fly")
    void ToscabotCanFly() throws InterruptedException {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/60469");
        WebElement toscaBot = driver.findElement(By.id("toscabot"));
        WebElement toscaBotTo = driver.findElement(By.id("to"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(toscaBot, toscaBotTo).perform();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Addition")
    void Addition() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/78264");
        String random1 = driver.findElement(By.id("no1")).getText();
        String random2 = driver.findElement(By.id("no2")).getText();
        int result = Integer.parseInt(random1) + Integer.parseInt(random2);
        driver.findElement(By.id("result")).sendKeys(String.valueOf(result));
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        driver.quit();
    }
}

