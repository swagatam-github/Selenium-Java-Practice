import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.common.GoodJob;
import pages.obstacles.AndCounting;
import pages.obstacles.NotATable;
import utils.Browser;
import utils.XMLParser;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static utils.BrowserWebDriver.openUrl;

public class ShawnFrost {
    WebDriver driver;

    @BeforeSuite
    void initObstaclePage() {
        driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Account/Login");
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

    @Test(testName = "And Counting")
    void AndCounting() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/24499");
        AndCounting andCounting = new AndCounting(driver);
        String chars = andCounting.getTypeThisText();
        long countOfOptions = andCounting.dropDown().getOptions()
                .stream().filter(option -> option.getAttribute("innerText").startsWith(chars))
                .count();
        andCounting.enterCount(String.valueOf(countOfOptions));
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Todo List")
    void Todolist() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/23292");
        List<WebElement> todoTable = driver.findElements(By.xpath("//table[@id='todo-tasks']//tr"))
                .stream().filter(row -> row.getAttribute("task") != null)
                .collect(Collectors.toList());
        WebElement completedTasksTable = driver.findElement(By.id("completed-tasks"));
        Actions actions = new Actions(driver);
        for (int i = 1; i <= todoTable.size(); i++) {
            int finalI = i;
            actions.dragAndDrop(
                            todoTable.stream()
                                    .filter(row -> Integer.parseInt(row.getAttribute("task")) == finalI)
                                    .findFirst().get(), completedTasksTable)
                    .perform();
        }
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Red Stripe")
    void RedStripe() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/30034");
        driver.findElement(By.id("generate")).click();
        driver.findElement(By.xpath("//div[@id='number']//following-sibling::div")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Future Christmas")
    void FutureChristmas() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/21269");
        String dayName = LocalDate.of(LocalDate.now().getYear(), 12, 25)
                .plusYears(2)
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        driver.findElement(By.id("christmasday")).sendKeys(dayName);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Be Fast - Automate")
    void BeFastAutomate() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/87912");
        driver.findElement(By.id("loadbooks")).click();
        String xmlString = driver.findElement(By.id("books")).getAttribute("value");
        String isbn = new XMLParser()
                .fromXMLString(xmlString)
                .getStringValueByXPath("//title[text()='Testing Computer Software']/following-sibling::isbn");
        driver.findElement(By.id("isbn")).sendKeys(isbn);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        driver.quit();
    }
}
