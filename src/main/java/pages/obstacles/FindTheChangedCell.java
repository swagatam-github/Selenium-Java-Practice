package pages.obstacles;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FindTheChangedCell extends PageFactory {
    WebDriver driver;

    @FindBy(id = "change")
    WebElement showChangedTableButton;

    @FindBy(id = "rowNumber")
    WebElement rowNumberTextBox;

    @FindBy(id = "columnNumber")
    WebElement columnNumberTextBox;

    @FindBy(id = "originalValue")
    WebElement originalValueTextBox;

    @FindBy(id = "changedValue")
    WebElement changedValueTextBox;

    @FindBy(id = "submit")
    WebElement submitButton;

    public FindTheChangedCell(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickChangeTableButton() {
        showChangedTableButton.click();
    }

    public void enterRowNumber(Object rowNumber) {
        rowNumberTextBox.sendKeys(rowNumber instanceof String ? (CharSequence) rowNumber : rowNumber.toString());
    }

    public void enterColumnNumber(Object columnNumber) {
        columnNumberTextBox.sendKeys(columnNumber instanceof String ? (CharSequence) columnNumber : columnNumber.toString());
    }

    public void enterOriginalValue(String originalValue) {
        originalValueTextBox.sendKeys(originalValue);
    }

    public void enterChangedValue(String changedValue) {
        changedValueTextBox.sendKeys(changedValue);
    }

    public void clickSubmit() {
        submitButton.click();
        // Temporary as the Submit button is not working in the UI
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("check()");
    }

    public List<List<String>> getTableData() {
        List<List<String>> table = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath("//div[@id='tableContent']//tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> colData = new ArrayList<>();
            for (WebElement cell : cells) {
                colData.add(cell.getText().trim());
            }
            table.add(colData);
        }
        return table;
    }
}
