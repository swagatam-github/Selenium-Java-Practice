package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoodJob extends PageFactory {
    WebDriver driver;

    @FindBy(xpath = "//h2[text()='Good job!']")
    WebElement successPopUp;

    public GoodJob(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isSuccessMessageShowed() {
        try {
            Thread.sleep(5000);
            return successPopUp.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
