package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.BiFunction;

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

    public static void scrollByAmount(WebDriver driver, int dX, int dY) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(" + dX + ", " + dY + ");");
    }
}
