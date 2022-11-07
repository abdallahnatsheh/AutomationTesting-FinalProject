package Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

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
}
