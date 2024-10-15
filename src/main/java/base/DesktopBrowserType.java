package base;

public interface DesktopBrowserType {
    String CHROME = "chrome";
    String EDGE = "edge";
    String FIREFOX = "firefox";
    String EDGE_HEADLESS = EDGE.concat("_headless");
    String CHROME_HEADLESS = CHROME.concat("_headless");
    String FIREFOX_HEADLESS = FIREFOX.concat("_headless");
}
