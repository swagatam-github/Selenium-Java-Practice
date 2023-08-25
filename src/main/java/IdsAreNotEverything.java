import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Browser;


import static utils.BrowserWebDriver.openUrl;

public class IdsAreNotEverything {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = openUrl(Browser.CHROME, "https://obstaclecourse.tricentis.com/Obstacles/22505/retry");
        Thread.sleep(5000);
        WebElement ClickMe = driver.findElement(By.xpath("//a[text()='Click me!']"));
        ClickMe.click();
        Thread.sleep(5000);
        if (driver.findElement(By.xpath("//h2[text()='Good job!']")).isDisplayed())
            System.out.println("Problem Solved");
        else
            System.out.println("Problem Not Solved");
        driver.close();
    }

}
