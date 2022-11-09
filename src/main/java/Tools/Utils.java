package Tools;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Utils {
    public static boolean IsElementPresent(By by, WebDriver driver) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException n) {
            return false;
        }
    }

    public static boolean IsElementPresent(By by, WebDriver driver, int sec) throws InterruptedException {
        boolean itemExist;

        itemExist = IsElementPresent(by, driver);
        while (!itemExist && sec >= 0) {
            Thread.sleep(1);
            itemExist = IsElementPresent(by, driver);
            sec--;
        }

        return sec != -1;
    }
    public static void timeToWait(double second) throws InterruptedException {
        double time = second * 1000;
        Thread.sleep((long) time);
    }
    public static  void waitForPageLoad(WebDriver driver) {
        WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"responsive-navbar-nav\"] ")));
    }
    public static Object[][] parseCsvFile(String filePath) throws Exception{
        List<String[]> lines = ReadCsvFile.readAllLines(filePath);
        lines.remove(0);
        Object[][] data = new Object[lines.size()][lines.get(0).length];
        int index = 0;
        for(String[] line : lines) {
            data[index] = line;
            index++;
        }
        return data;
    }
}
