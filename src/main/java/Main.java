import org.openqa.selenium.WebDriver;
import utils.Browser;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new Browser().openBrowser(Browser.Browsers.EDGE);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.quit();
    }
}
