import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Browser;

import java.util.List;

import static utils.BrowserWebDriver.openUrl;

public class NotATable {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Obstacles/64161");

        driver.findElement(By.id("generate")).click();
        Thread.sleep(5000);
        List<WebElement> table = driver.findElements(By.xpath("//div[@class='row propertyGrid']"));
        String orderId = "";
        for (int i = 1; i <= table.size(); i++) {
            if (driver.findElement(By.xpath("(//div[@class='col-md-4 bg-info border'])[" + i + "]")).getText().equals("order id"))
                orderId = driver.findElement(By.xpath("(//div[@class='col-md-4 border'])[" + i + "]")).getText();
        }
        driver.findElement(By.id("offerId")).sendKeys(orderId);

        try {
            Thread.sleep(5000);
            if (driver.findElement(By.xpath("//h2[text()='Good job!']")).isDisplayed())
                System.out.println("Problem Solved");
        } catch (Exception e) {
            System.out.println("Problem Not Solved");
        } finally {
            driver.quit();
        }
    }
}
