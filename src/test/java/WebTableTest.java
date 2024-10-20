import base.Browser;
import base.DesktopBrowserType;
import base.WebTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.common.GoodJob;
import pages.obstacles.FindTheChangedCell;

import java.util.List;

public class WebTableTest {
    WebDriver driver;

    @BeforeSuite
    void initObstaclePage() {
        driver = new Browser().openBrowser(DesktopBrowserType.EDGE);
    }

    @Test(testName = "Fun With Tables", enabled = false)
    void FunWithTables() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/92248");
        WebElement table = driver.findElement(By.id("persons"));
        WebTable personsTable = new WebTable(driver, table);

        //---------
        WebElement desiredRow = driver.findElement(By.xpath("//table[@id='persons']//tr[td[1][text()='John'] and td[2][text()='Doe']]"));
        WebElement editButton = desiredRow.findElement(By.xpath(".//td[4]//button[@name='edit']"));
        editButton.click();

        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Lots Of Rows")
    void LotsOfRows() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41032");
        WebTable table = new WebTable(driver, driver.findElement(By.id("rowCountTable")));
        driver.findElement(By.id("rowcount")).sendKeys(String.valueOf(table.getRowCountIncludingHeader()));
        driver.findElement(By.id("sample")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "The Last Row", enabled = false)
    void TheLastRow() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/70310");
        WebTable table = new WebTable(driver, driver.findElement(By.id("orderTable")));
        table.findRowByCellText("Order Value", 1);

        // -----------
        List<WebElement> amounts = driver.findElements(By.xpath("//table[@id='orderTable']//tr[td[1][text()='Order Value']]/td[2]"));
        String amount = amounts.get(amounts.size() - 1).getText();
        driver.findElement(By.id("ordervalue")).sendKeys(amount);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Table Search")
    void TableSearch() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41036");
        WebTable table = new WebTable(driver, driver.findElement(By.id("randomTable")));
        driver.findElement(By.id("resulttext")).sendKeys(table.isValuePresent("15") ? "True" : "False");
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Meeting Scheduler")
    void MeetingScheduler() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41037");

        WebElement timeTable = driver.findElement(By.id("timeTable"));
        WebElement headerCell = timeTable.findElement(By.xpath("//th[text()='Thursday']"));
        WebElement timeSlot = timeTable.findElement(By.xpath("//tr[td[1][text()='11:00 - 13:00']]"));

        int cellIndex = Integer.parseInt(headerCell.getAttribute("cellIndex"));
        String roomStatus = timeSlot.findElement(By.xpath(String.format("./td[%d]", cellIndex + 1))).getText();
        //String roomStatus = timeSlot.findElement(By.xpath("./td[" + (cellIndex + 1) + "]")).getText();

        driver.findElement(By.id("resulttext")).sendKeys(roomStatus);

        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Find The Changed Cell")
    void FindTheChangedCell() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73591");
        FindTheChangedCell findTheChangedCell = new FindTheChangedCell(driver);

        List<List<String>> originalTable = findTheChangedCell.getTableData();
        findTheChangedCell.clickChangeTableButton();
        List<List<String>> changedTable = findTheChangedCell.getTableData();

        for (int i = 0; i < originalTable.size(); i++) {
            for (int j = 0; j < originalTable.get(0).size(); j++) {
                String originalStr = originalTable.get(i).get(j);
                String changedStr = changedTable.get(i).get(j);
                if (!originalStr.equals(changedStr)) {
                    findTheChangedCell.enterRowNumber(i + 1);
                    findTheChangedCell.enterColumnNumber(j + 1);
                    findTheChangedCell.enterOriginalValue(originalStr);
                    findTheChangedCell.enterChangedValue(changedStr);
                    break;
                }
            }
        }

        findTheChangedCell.clickSubmit();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        if (driver != null)
            driver.quit();
    }
}
