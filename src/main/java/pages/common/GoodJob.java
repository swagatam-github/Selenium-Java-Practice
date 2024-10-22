package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoodJob extends PageFactory {
    WebDriver driver;

    @FindBy(xpath = "//h2[text()='Good job!']")
    WebElement successPopUp;

    public GoodJob(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessMessageShowed() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return webDriverWait.until(ExpectedConditions.visibilityOf(successPopUp))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
