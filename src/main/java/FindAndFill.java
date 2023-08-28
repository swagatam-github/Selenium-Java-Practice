import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Browser;

import static utils.BrowserWebDriver.openUrl;

public class FindAndFill {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Obstacles/73590");
        Thread.sleep(5000);
        WebElement pass = driver.findElement(By.id("pass"));
              pass.clear();
              pass.sendKeys("ABC");
        driver.findElement(By.id("sample")).click();
        try {
            Thread.sleep(5000);
            if (driver.findElement(By.xpath("//h2[text()='Good job!']")).isDisplayed())
                System.out.println("Problem Solved");
        } catch (Exception e) {
            System.out.println("Problem Not Solved");
        } finally {
            //driver.quit();
        }
    }
}
