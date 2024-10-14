package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Browser {
    public WebDriver openBrowser(String browserName) {
        browserName = browserName.toLowerCase();

        WebDriver driver;
        switch (browserName) {
            case "chrome":
                driver = openChromeBrowser();
                break;
            case "firefox":
                driver = openFirefoxBrowser();
                break;
            case "chrome_headless":
                driver = openHeadlessChrome();
                break;
            case "edge_headless":
                driver = openHeadlessEdge();
                break;
            case "edge":
            default:
                driver = openEdgeBrowser();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver openChromeBrowser() {
        return new ChromeDriver();
    }

    private WebDriver openFirefoxBrowser() {
        return new FirefoxDriver();
    }

    private WebDriver openHeadlessChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        return new ChromeDriver(options);
    }

    private WebDriver openHeadlessEdge() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        return new EdgeDriver(options);
    }

    private WebDriver openEdgeBrowser() {
        return new EdgeDriver();
    }
}