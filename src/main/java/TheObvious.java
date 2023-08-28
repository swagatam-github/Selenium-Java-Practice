import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.Browser;


import static utils.BrowserWebDriver.openUrl;

public class TheObvious {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Obstacles/73588");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//a[text()='Generate Random Text']")).click();
        String randomText = driver.findElement(By.id("randomtext")).getAttribute("value");
        Select dropDown = new Select(driver.findElement(By.id("selectlink")));
        dropDown.selectByVisibleText(randomText);
        driver.findElement(By.id("submitanswer")).click();
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
