package pages.obstacles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AndCounting extends PageFactory {
    WebDriver driver;

    @FindBy(id = "autocomplete")
    WebElement dropDown;

    @FindBy(id = "entryCount")
    WebElement countTextBox;

    @FindBy(id = "typeThis")
    WebElement typeThisLabel;

    public AndCounting(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Select dropDown() {
        return new Select(dropDown);
    }

    public void enterCount(String count) {
        countTextBox.clear();
        countTextBox.sendKeys(count);
    }

    public String getTypeThisText() {
        return typeThisLabel.getText().trim();
    }
}
