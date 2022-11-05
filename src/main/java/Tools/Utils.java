package Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Utils {
    public static Boolean IsElementPresent(By by, WebDriver driver) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException n) {
            return false;
        }
    }

    public static Boolean IsElementPresent(By by, WebDriver driver, int sec) throws InterruptedException {
        Boolean itemExist = false;

        itemExist = IsElementPresent(by, driver);
        while (!itemExist && sec >= 0) {
            Thread.sleep(1);
            itemExist = IsElementPresent(by, driver);
            sec--;
        }

        if (sec == -1)
            return false;
        else
            return true;
    }
}
