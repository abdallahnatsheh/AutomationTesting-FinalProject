package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;

import static Tools.Utils.timeToWait;

public class MainMenuPage {
    WebDriver driver;
    ArrayList<WebElement> menuList;
////*[@id="root"]//*/main
    final String menuListXpath = "//*[@class='item menuItems']";
    final String itemsXpath = "div[1]/p";

    public MainMenuPage (WebDriver driver){
        this.driver = driver;
        this.menuList = (ArrayList<WebElement>) driver.findElements(By.xpath(menuListXpath));
    }

    public int getMenuItemIndex(String itemName){
        for(int i=0;i<menuList.size();i++) {
            WebElement item = menuList.get(i).findElement(By.xpath(itemsXpath));
            String text = item.getText();
            if (itemName.equals(text))
                return i;
        }
        return -1;
    }

    public void clickOnMenuItem(int index) throws InterruptedException {
        WebElement item = menuList.get(index);
        item.click();
        timeToWait(1);
    }


}
