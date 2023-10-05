package pages.obstacles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ToscaBotCanFly extends PageFactory {
    WebDriver driver;

    @FindBy(id = "toscabot")
    WebElement toscaBot;

    @FindBy(id = "to")
    WebElement targetLocation;

    public ToscaBotCanFly(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getToscaBot() {
        return toscaBot;
    }

    public WebElement getTargetLocation() {
        return targetLocation;
    }
}
