import org.openqa.selenium.WebDriver;
import utils.Browser;

import static utils.BrowserWebDriver.openUrl;

public class Main {
    public static void main(String[] args)
    {
        WebDriver driver = openUrl(Browser.EDGE, "https://www.google.com/");

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.close();
    }
}
