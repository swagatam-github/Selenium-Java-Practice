package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class WebTable {

    private WebDriver driver;
    private WebElement tableElement;

    // Constructor to initialize WebTable with the WebElement representing the table
    public WebTable(WebDriver driver, WebElement tableElement) {
        this.driver = driver;
        this.tableElement = tableElement;
    }

    // Method to get the number of rows in the table (excluding headers)
    public int getRowCount() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tr"));
        return rows.size() - 1;  // Assuming the first row is the header
    }

    // Method to get the number of rows in the table (including headers)
    public int getRowCountIncludingHeader() {
        List<WebElement> rows = tableElement.findElements(By.xpath(".//tr"));
        return rows.size();
    }

    // Method to get the number of columns in the table
    public int getColumnCount() {
        List<WebElement> columns = tableElement.findElements(By.xpath(".//tr[1]/th"));
        return columns.size();
    }

    // Method to get the text from a specific cell
    public String getCellText(int row, int column) {
        WebElement cell = tableElement.findElement(By.xpath(".//tr[" + (row + 1) + "]/td[" + column + "]"));
        return cell.getText();
    }

    // Method to get all data from the table as a list of lists (rows and columns)
    public List<List<String>> getTableData() {
        return tableElement.findElements(By.xpath(".//tr")).stream()
                .map(row -> row.findElements(By.xpath(".//td"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    // Method to find a row based on cell text in a specific column
    public WebElement findRowByCellText(String cellText, int columnIndex) {
        return tableElement.findElement(By.xpath(".//tr[td[" + columnIndex + "]='" + cellText + "']"));
    }

    // Method to click a button within a specific cell
    public void clickButtonInCell(int row, int column, String buttonName) {
        WebElement button = tableElement.findElement(By.xpath(".//tr[" + (row + 1) + "]/td[" + column + "]//button[@name='" + buttonName + "']"));
        button.click();
    }

    // Method to get all column headers
    public List<String> getColumnHeaders() {
        return tableElement.findElements(By.xpath(".//tr[1]/th")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Method to find a cell by its row and column indices
    public WebElement getCell(int row, int column) {
        return tableElement.findElement(By.xpath(".//tr[" + (row + 1) + "]/td[" + column + "]"));
    }

    // Method to get the value from a specific header column
    public String getValueFromHeaderColumn(String header, int rowIndex) {
        int columnIndex = getColumnHeaders().indexOf(header) + 1;
        if (columnIndex == 0) {
            throw new IllegalArgumentException("Header not found: " + header);
        }
        return getCellText(rowIndex, columnIndex);
    }

    // Method to find if a value exists in the table
    public boolean isValuePresent(String value) {
        return tableElement.findElements(By.xpath(".//td"))
                .stream()
                .anyMatch(cell -> cell.getText().equals(value));
    }

    // Method to get a list of values from a specific column
    public List<String> getColumnData(int columnIndex) {
        return tableElement.findElements(By.xpath(".//tr/td[" + columnIndex + "]")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
