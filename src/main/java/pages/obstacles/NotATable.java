package pages.obstacles;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotATable extends PageFactory {
    WebDriver driver;

    @FindBy(id = "generate")
    WebElement generateOrderIdButton;

    @FindBy(xpath = "//div[text()='order id']/following-sibling::div")
    WebElement orderId;

    @FindBy(id = "offerId")
    WebElement offerIdTextBox;

    public NotATable(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickGenerateOrderId() {
        generateOrderIdButton.click();
    }

    public String getOrderID() {
        return orderId.getText().trim();
    }

    public void enterOrderId(String orderId) {
        offerIdTextBox.clear();
        offerIdTextBox.sendKeys(orderId);
    }

    public void enterOrderId() {
        enterOrderId(getOrderID());
    }
}
