package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementOperations {
    public static void staticWait(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clickElementByOffSet(WebDriver driver, WebElement element, int widthOffsetPercent, int heightOffsetPercent) {
        BiFunction<Integer, Integer, Integer> calculateOffset = (Integer a, Integer percent) -> (int) ((a * -0.5) + (a * (percent / 100.0)));

        int widthOffset = calculateOffset.apply(element.getRect().getDimension().getWidth(), widthOffsetPercent);
        int heightOffset = calculateOffset.apply(element.getRect().getDimension().getHeight(), heightOffsetPercent);

        new Actions(driver).moveToElement(element, widthOffset, heightOffset).click().perform();
    }

    public static Matcher regexExtractor(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher;
    }

    public static List<String> regexMultiTextExtractor(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        List<String> arr = new ArrayList<>();
        while (matcher.find()) {
            arr.add(matcher.group());
        }
        return arr;
    }

    public static void scrollByAmount(WebDriver driver, int dX, int dY) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(" + dX + ", " + dY + ");");
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void triggerClick(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
