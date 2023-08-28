import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Browser;

import static utils.BrowserWebDriver.openUrl;

public class NotATable {
    public static void main(String[] args) {
        WebDriver driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Obstacles/64161");

        driver.findElement(By.id("generate")).click();
        String orderId = driver
                .findElement(By.xpath("//div[text()='order id']/following-sibling::div"))
                .getText().trim();
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
