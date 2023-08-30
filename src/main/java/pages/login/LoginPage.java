package pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends PageFactory {
    WebDriver driver;

    @FindBy(name = "UserName")
    WebElement emailTextBox;

    @FindBy(name = "Password")
    WebElement passwordTextBox;

    @FindBy(xpath = "//button[text()='Login']")
    WebElement loginButton;

    @FindBy(xpath = "//a[text()='Account']")
    WebElement accountButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        emailTextBox.sendKeys(username);
        passwordTextBox.sendKeys(password);
        loginButton.click();
        if (accountButton.isDisplayed())
            System.out.println("Logged In to the Account");
        else
            System.out.println("Not Logged In!");
    }
}
