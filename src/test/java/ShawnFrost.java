import base.Browser;
import base.DesktopBrowserType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.common.GoodJob;
import pages.obstacles.AndCounting;
import pages.obstacles.FindTheChangedCell;
import pages.obstacles.NotATable;
import pages.obstacles.ToscaBotCanFly;
import utils.XMLParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static utils.ElementOperations.*;

public class ShawnFrost {
    WebDriver driver;

    @BeforeSuite
    void initObstaclePage() {
        driver = new Browser().openBrowser(DesktopBrowserType.EDGE);
    }

    @Test(testName = "Not a table")
    void NotATable() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/64161");
        NotATable notATable = new NotATable(driver);
        notATable.clickGenerateOrderId();
        String orderId = notATable.getOrderID();
        notATable.enterOrderId(orderId);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "And Counting")
    void AndCounting() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/24499");
        AndCounting andCounting = new AndCounting(driver);
        String chars = andCounting.getTypeThisText();
        long countOfOptions = andCounting.dropDown().getOptions()
                .stream().filter(option -> option.getAttribute("innerText").startsWith(chars))
                .count();
        andCounting.enterCount(String.valueOf(countOfOptions));
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Todo List")
    void Todolist() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/23292");
        List<WebElement> todoTable = driver.findElements(By.xpath("//table[@id='todo-tasks']//tr"))
                .stream().filter(row -> row.getAttribute("task") != null)
                .collect(Collectors.toList());
        WebElement completedTasksTable = driver.findElement(By.id("completed-tasks"));
        Actions actions = new Actions(driver);
        for (int i = 1; i <= todoTable.size(); i++) {
            int finalI = i;
            actions.dragAndDrop(
                            todoTable.stream()
                                    .filter(row -> Integer.parseInt(row.getAttribute("task")) == finalI)
                                    .findFirst().get(), completedTasksTable)
                    .perform();
        }
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Red Stripe")
    void RedStripe() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/30034");
        driver.findElement(By.id("generate")).click();
        driver.findElement(By.xpath("//div[@id='number']//following-sibling::div")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Future Christmas")
    void FutureChristmas() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/21269");
        String dayName = LocalDate.of(LocalDate.now().getYear(), 12, 25)
                .plusYears(2)
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        driver.findElement(By.id("christmasday")).sendKeys(dayName);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Be Fast - Automate")
    void BeFastAutomate() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/87912");
        driver.findElement(By.id("loadbooks")).click();
        String xmlString = driver.findElement(By.id("books")).getAttribute("value");
        String isbn = new XMLParser()
                .fromXMLString(xmlString)
                .getStringValueByXPath("//title[text()='Testing Computer Software']/following-sibling::isbn");
        driver.findElement(By.id("isbn")).sendKeys(isbn);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tricentis Tosca Olympics")
    void TricentisToscaOlympics() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/82018");
        WebElement startButton = driver.findElement(By.id("start"));
        startButton.click();
        WebElement instructions = driver.findElement(By.className("instructions"));
        String winMessage = "You did it!";
        String crashMessage = "Crash!";
        while (!instructions.getText().trim().equals(winMessage)) {
            String op = instructions.getText().trim();
            switch (op) {
                case "Go right!":
                    new Actions(driver).sendKeys(Keys.ARROW_RIGHT).perform();
                    break;
                case "Go left!":
                    new Actions(driver).sendKeys(Keys.ARROW_LEFT).perform();
                    break;
            }
            if (op.equals(crashMessage)) startButton.click();
        }
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Find The Changed Cell")
    void FindTheChangedCell() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73591");
        FindTheChangedCell findTheChangedCell = new FindTheChangedCell(driver);

        List<List<String>> originalTable = findTheChangedCell.getTableData();
        findTheChangedCell.clickChangeTableButton();
        List<List<String>> changedTable = findTheChangedCell.getTableData();

        for (int i = 0; i < originalTable.size(); i++) {
            for (int j = 0; j < originalTable.get(0).size(); j++) {
                String originalStr = originalTable.get(i).get(j);
                String changedStr = changedTable.get(i).get(j);
                if (!originalStr.equals(changedStr)) {
                    findTheChangedCell.enterRowNumber(i + 1);
                    findTheChangedCell.enterColumnNumber(j + 1);
                    findTheChangedCell.enterOriginalValue(originalStr);
                    findTheChangedCell.enterChangedValue(changedStr);
                    break;
                }
            }
        }

        findTheChangedCell.clickSubmit();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Find The Changed Cell Using Inner HTML")
    void FindTheChangedCellUsingInnerHTML() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73591");
        FindTheChangedCell findTheChangedCell = new FindTheChangedCell(driver);

        Function<WebElement, HashMap<String, String>> extractTableData = (WebElement tableElement) -> {
            String tableHTML = tableElement.getAttribute("innerHTML");
            List<String> cells = regexMultiTextExtractor(tableHTML, "<td id=\"\\d+_\\d+\">[\\w|\\d]{4}<\\/td>");
            HashMap<String, String> tableDataMap = new HashMap<>();
            for (String cell : cells) {
                Matcher matcher = regexExtractor(cell, "<td id=\"(?<key>\\d+_\\d+)\">(?<cell>[\\w|\\d]{4})<\\/td>");
                tableDataMap.put(matcher.group("key"), matcher.group("cell"));
            }
            return tableDataMap;
        };

        WebElement table = driver.findElement(By.xpath("//div[@id='tableContent']"));
        HashMap<String, String> originalTableData = extractTableData.apply(table);
        findTheChangedCell.clickChangeTableButton();
        HashMap<String, String> newTableData = extractTableData.apply(table);

        for (String key : originalTableData.keySet()) {
            String originalData = originalTableData.get(key);
            String newData = newTableData.get(key);

            if (!originalData.equals(newData)) {
                System.out.println("New Implementation");
                String[] rowCol = key.split("_");
                System.out.printf("Row: %s\nCol: %s\nActual Value: %s\nExpected Value: %s%n",
                        rowCol[0], rowCol[1], originalData, newData);
                findTheChangedCell.enterRowNumber(rowCol[0]);
                findTheChangedCell.enterColumnNumber(rowCol[1]);
                findTheChangedCell.enterOriginalValue(originalData);
                findTheChangedCell.enterChangedValue(newData);
            }
        }

        findTheChangedCell.clickSubmit();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Halfway")
    void Halfway() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41038");
        WebElement halfButton = driver.findElement(By.id("halfButton"));
        clickElementByOffSet(driver, halfButton, 75, 50);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tomorrow")
    void Tomorrow() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/19875");
        String tomorrowsDate = LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        driver.findElement(By.id("datefield")).sendKeys(tomorrowsDate);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Math")
    void Math() {
        Map<String, BinaryOperator<Integer>> operatorMap = new HashMap<>();
        operatorMap.put("+", Integer::sum);
        operatorMap.put("-", (a, b) -> a - b);
        operatorMap.put("*", (a, b) -> a * b);
        operatorMap.put("%", (a, b) -> a % b);

        driver.get("https://obstaclecourse.tricentis.com/Obstacles/32403");
        int number1 = Integer.parseInt(driver.findElement(By.id("no1")).getText().trim());
        int number2 = Integer.parseInt(driver.findElement(By.id("no2")).getText().trim());
        String operator = driver.findElement(By.id("symbol1")).getText().trim();
        int result = operatorMap.get(operator).apply(number1, number2);
        driver.findElement(By.id("result")).sendKeys(String.valueOf(result));
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Extracting Text")
    void ExtractingText() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/81012");
        String successMessage = driver.findElement(By.id("alerttext")).getText().trim();
        String amount = regexExtractor(successMessage, "\\$(?<Amount>\\d+\\.\\d+)")
                .group("Amount");
        driver.findElement(By.id("totalamountText")).sendKeys(amount);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tough Cookie")
    void ToughCookie() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/45618");
        WebElement txtBox = driver.findElement(By.id("generated"));
        txtBox.click();
        String randomText = txtBox.getAttribute("value").trim();
        List<String> numbers = regexMultiTextExtractor(randomText, "\\d+");
        driver.findElement(By.id("firstNumber")).sendKeys(numbers.get(0));
        driver.findElement(By.id("secondNumber")).sendKeys(numbers.get(1));
        driver.findElement(By.id("thirdNumber")).sendKeys(numbers.get(2));
        txtBox.click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Hidden Element")
    void HiddenElement() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/66666");
        driver.findElement(By.id("clickthis")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Addition")
    void Addition() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/78264");
        int number1 = Integer.parseInt(driver.findElement(By.id("no1")).getText().trim());
        int number2 = Integer.parseInt(driver.findElement(By.id("no2")).getText().trim());
        driver.findElement(By.id("result")).sendKeys(String.valueOf(number1 + number2));
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Empty")
    void Empty() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/66667");
        driver.findElement(By.id("generate")).click();
        List<WebElement> checkpoints = driver.findElements(By.className("checkpoint"));
        for (WebElement checkpoint : checkpoints) {
            checkpoint.click();
        }
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "ToscaBot Can Fly")
    void ToscaBotCanFly() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/60469");
        ToscaBotCanFly toscaBotCanFly = new ToscaBotCanFly(driver);
        new Actions(driver)
                .dragAndDrop(toscaBotCanFly.getToscaBot(), toscaBotCanFly.getTargetLocation())
                .perform();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Bubble Sort")
    void BubbleSort() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/73589");
        String xpath = "(//div[@id='array']/div[@class='bubble']/div[@class='num'])[%d]";
        try {
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    staticWait(1);
                    int number1 = Integer.parseInt(driver.findElement(By.xpath(String.format(xpath, 1))).getText());
                    int number2 = Integer.parseInt(driver.findElement(By.xpath(String.format(xpath, 2))).getText());
                    if (number1 > number2) {
                        driver.findElement(By.id("button1")).click();
                    }
                    driver.findElement(By.id("button2")).click();
                }
            }
        } catch (Exception ignored) {
        } finally {
            Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
        }
    }

    @Test(testName = "Testing Methods")
    void TestingMethods() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/94441");
        String[] testingTypes = {
                "Functional", "End2End", "GUI", "Exploratory"
        };
        Select multiSelect = new Select(driver.findElement(By.id("multiselect")));
        for (String testingType : testingTypes) {
            multiSelect.selectByVisibleText(testingType.concat(" testing"));
        }
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "DropDown Table")
    void DropDownTable() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/14090");
        driver.findElement(By.id("generate")).click();
        List<WebElement> tasks = driver.findElements(By.className("task"));
        tasks.remove(0);
        List<WebElement> values = driver.findElements(By.xpath("//select[@class='tableselect value']"));
        for (int i = 0; i < tasks.size(); i++) {
            String firstLetter = tasks.get(i).getText().split(":")[1].trim();
            Select select = new Select(values.get(i));
            select.getOptions().stream()
                    .filter(element -> element.getText().startsWith(firstLetter))
                    .findFirst()
                    .ifPresent(option -> select.selectByVisibleText(option.getText()));
        }
        driver.findElement(By.id("submit")).click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Click Me If You Can")
    void ClickMeIfYouCan() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/41040");
        WebElement button = driver.findElement(By.id("buttontoclick"));
        triggerClick(driver, button);
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "TestData In A Service")
    void TestDataInAService() {
        driver.get("https://obstaclecourse.tricentis.com/Obstacles/16384");
        WebElement createDataButton = driver.findElement(By.id("createTDS"));
        createDataButton.click();
        WebElement keyTextBox = driver.findElement(By.id("key"));
        WebElement attributeTextBox = driver.findElement(By.id("attribute"));
        String key = keyTextBox.getText().trim();
        String attribute = attributeTextBox.getText().trim();

        staticWait(3);
        // Steps to get the value from TDS (Tricentis Test Data Service)
        String tdsUri = "https://tdsservice.azurewebsites.net";
        String repository = "data";
        String tdsType = "obstacle";

        Response response = given()
                .baseUri(tdsUri)
                .get(repository + "/" + tdsType);

        List<Map<String, Object>> data = response.body().jsonPath().getList("$");
        Map<String, Object> r = data.stream()
                .filter(e -> ((Map<String, Object>) e.get("data")).get("key").equals(key))
                .findFirst()
                .orElse(null);
        String result = ((Map<String, Object>) r.get("data")).get(attribute).toString();
        System.out.printf("key => %s\nattribute => %s\nresult => %s", key, attribute, result);

        WebElement resultTextBox = driver.findElement(By.id("result"));
        resultTextBox.sendKeys(result);
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        Assert.assertTrue(new GoodJob(driver).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterSuite
    void tearDownSession() {
        if (driver != null)
            driver.quit();
    }
}
