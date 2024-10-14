/*
Add "Test runner params" mentioned below
        -parallel methods -threadcount 6
*/

import base.Browser;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static utils.ElementOperations.*;

public class ParallelRunTestCase {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod
    void initObstaclePage() {
        driver.set(new Browser().openBrowser("edge"));
    }

    @Test(testName = "Not a table")
    void NotATable() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/64161");
        NotATable notATable = new NotATable(driver.get());
        notATable.clickGenerateOrderId();
        String orderId = notATable.getOrderID();
        notATable.enterOrderId(orderId);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "And Counting")
    void AndCounting() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/24499");
        AndCounting andCounting = new AndCounting(driver.get());
        String chars = andCounting.getTypeThisText();
        long countOfOptions = andCounting.dropDown().getOptions()
                .stream().filter(option -> option.getAttribute("innerText").startsWith(chars))
                .count();
        andCounting.enterCount(String.valueOf(countOfOptions));
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Todo List")
    void Todolist() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/23292");
        List<WebElement> todoTable = driver.get().findElements(By.xpath("//table[@id='todo-tasks']//tr"))
                .stream().filter(row -> row.getAttribute("task") != null)
                .collect(Collectors.toList());
        WebElement completedTasksTable = driver.get().findElement(By.id("completed-tasks"));
        Actions actions = new Actions(driver.get());
        for (int i = 1; i <= todoTable.size(); i++) {
            int finalI = i;
            actions.dragAndDrop(
                            todoTable.stream()
                                    .filter(row -> Integer.parseInt(row.getAttribute("task")) == finalI)
                                    .findFirst().get(), completedTasksTable)
                    .perform();
        }
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Red Stripe")
    void RedStripe() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/30034");
        driver.get().findElement(By.id("generate")).click();
        driver.get().findElement(By.xpath("//div[@id='number']//following-sibling::div")).click();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Future Christmas")
    void FutureChristmas() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/21269");
        String dayName = LocalDate.of(LocalDate.now().getYear(), 12, 25)
                .plusYears(2)
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        driver.get().findElement(By.id("christmasday")).sendKeys(dayName);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Be Fast - Automate")
    void BeFastAutomate() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/87912");
        driver.get().findElement(By.id("loadbooks")).click();
        String xmlString = driver.get().findElement(By.id("books")).getAttribute("value");
        String isbn = new XMLParser()
                .fromXMLString(xmlString)
                .getStringValueByXPath("//title[text()='Testing Computer Software']/following-sibling::isbn");
        driver.get().findElement(By.id("isbn")).sendKeys(isbn);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tricentis Tosca Olympics")
    void TricentisToscaOlympics() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/82018");
        WebElement startButton = driver.get().findElement(By.id("start"));
        startButton.click();
        WebElement instructions = driver.get().findElement(By.className("instructions"));
        String winMessage = "You did it!";
        String crashMessage = "Crash!";
        while (!instructions.getText().trim().equals(winMessage)) {
            String op = instructions.getText().trim();
            switch (op) {
                case "Go right!":
                    new Actions(driver.get()).sendKeys(Keys.ARROW_RIGHT).perform();
                    break;
                case "Go left!":
                    new Actions(driver.get()).sendKeys(Keys.ARROW_LEFT).perform();
                    break;
            }
            if (op.equals(crashMessage)) startButton.click();
        }
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Find The Changed Cell")
    void FindTheChangedCell() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/73591");
        FindTheChangedCell findTheChangedCell = new FindTheChangedCell(driver.get());

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
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Halfway")
    void Halfway() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/41038");
        WebElement halfButton = driver.get().findElement(By.id("halfButton"));
        clickElementByOffSet(driver.get(), halfButton, 75, 50);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tomorrow")
    void Tomorrow() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/19875");
        String tomorrowsDate = LocalDate.now()
                .plusDays(1)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        driver.get().findElement(By.id("datefield")).sendKeys(tomorrowsDate);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Math")
    void Math() {
        Map<String, BinaryOperator<Integer>> operatorMap = new HashMap<>();
        operatorMap.put("+", Integer::sum);
        operatorMap.put("-", (a, b) -> a - b);
        operatorMap.put("*", (a, b) -> a * b);
        operatorMap.put("%", (a, b) -> a % b);

        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/32403");
        int number1 = Integer.parseInt(driver.get().findElement(By.id("no1")).getText().trim());
        int number2 = Integer.parseInt(driver.get().findElement(By.id("no2")).getText().trim());
        String operator = driver.get().findElement(By.id("symbol1")).getText().trim();
        int result = operatorMap.get(operator).apply(number1, number2);
        driver.get().findElement(By.id("result")).sendKeys(String.valueOf(result));
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Extracting Text")
    void ExtractingText() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/81012");
        String successMessage = driver.get().findElement(By.id("alerttext")).getText().trim();
        String amount = regexExtractor(successMessage, "\\$(?<Amount>\\d+\\.\\d+)")
                .group("Amount");
        driver.get().findElement(By.id("totalamountText")).sendKeys(amount);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Tough Cookie")
    void ToughCookie() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/45618");
        WebElement txtBox = driver.get().findElement(By.id("generated"));
        txtBox.click();
        String randomText = txtBox.getAttribute("value").trim();
        List<String> numbers = regexMultiTextExtractor(randomText, "\\d+");
        driver.get().findElement(By.id("firstNumber")).sendKeys(numbers.get(0));
        driver.get().findElement(By.id("secondNumber")).sendKeys(numbers.get(1));
        driver.get().findElement(By.id("thirdNumber")).sendKeys(numbers.get(2));
        txtBox.click();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Hidden Element")
    void HiddenElement() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/66666");
        driver.get().findElement(By.id("clickthis")).click();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Addition")
    void Addition() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/78264");
        int number1 = Integer.parseInt(driver.get().findElement(By.id("no1")).getText().trim());
        int number2 = Integer.parseInt(driver.get().findElement(By.id("no2")).getText().trim());
        driver.get().findElement(By.id("result")).sendKeys(String.valueOf(number1 + number2));
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Empty")
    void Empty() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/66667");
        driver.get().findElement(By.id("generate")).click();
        List<WebElement> checkpoints = driver.get().findElements(By.className("checkpoint"));
        for (WebElement checkpoint : checkpoints) {
            checkpoint.click();
        }
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "ToscaBot Can Fly")
    void ToscaBotCanFly() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/60469");
        ToscaBotCanFly toscaBotCanFly = new ToscaBotCanFly(driver.get());
        new Actions(driver.get())
                .dragAndDrop(toscaBotCanFly.getToscaBot(), toscaBotCanFly.getTargetLocation())
                .perform();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Bubble Sort")
    void BubbleSort() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/73589");
        String xpath = "(//div[@id='array']/div[@class='bubble']/div[@class='num'])[%d]";
        try {
            for (int i = 1; i <= 9; i++) {
                for (int j = 1; j <= 9; j++) {
                    staticWait(1);
                    int number1 = Integer.parseInt(driver.get().findElement(By.xpath(String.format(xpath, 1))).getText());
                    int number2 = Integer.parseInt(driver.get().findElement(By.xpath(String.format(xpath, 2))).getText());
                    if (number1 > number2) {
                        driver.get().findElement(By.id("button1")).click();
                    }
                    driver.get().findElement(By.id("button2")).click();
                }
            }
        } catch (Exception ignored) {
        } finally {
            Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
        }
    }

    @Test(testName = "Testing Methods")
    void TestingMethods() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/94441");
        String[] testingTypes = {
                "Functional", "End2End", "GUI", "Exploratory"
        };
        Select multiSelect = new Select(driver.get().findElement(By.id("multiselect")));
        for (String testingType : testingTypes) {
            multiSelect.selectByVisibleText(testingType.concat(" testing"));
        }
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "DropDown Table")
    void DropDownTable() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/14090");
        driver.get().findElement(By.id("generate")).click();
        List<WebElement> tasks = driver.get().findElements(By.className("task"));
        tasks.remove(0);
        List<WebElement> values = driver.get().findElements(By.xpath("//select[@class='tableselect value']"));
        for (int i = 0; i < tasks.size(); i++) {
            String firstLetter = tasks.get(i).getText().split(":")[1].trim();
            Select select = new Select(values.get(i));
            select.getOptions().stream()
                    .filter(element -> element.getText().startsWith(firstLetter))
                    .findFirst()
                    .ifPresent(option -> select.selectByVisibleText(option.getText()));
        }
        driver.get().findElement(By.id("submit")).click();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "Click Me If You Can")
    void ClickMeIfYouCan() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/41040");
        WebElement button = driver.get().findElement(By.id("buttontoclick"));
        triggerClick(driver.get(), button);
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @Test(testName = "TestData In A Service")
    void TestDataInAService() {
        driver.get().get("https://obstaclecourse.tricentis.com/Obstacles/16384");
        WebElement createDataButton = driver.get().findElement(By.id("createTDS"));
        createDataButton.click();
        WebElement keyTextBox = driver.get().findElement(By.id("key"));
        WebElement attributeTextBox = driver.get().findElement(By.id("attribute"));
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

        WebElement resultTextBox = driver.get().findElement(By.id("result"));
        resultTextBox.sendKeys(result);
        WebElement submitButton = driver.get().findElement(By.id("submit"));
        submitButton.click();
        Assert.assertTrue(new GoodJob(driver.get()).isSuccessMessageShowed(), "Problem Not Solved");
    }

    @AfterMethod
    void tearDownSession() {
        if (driver.get() != null)
            driver.get().quit();
    }
}
