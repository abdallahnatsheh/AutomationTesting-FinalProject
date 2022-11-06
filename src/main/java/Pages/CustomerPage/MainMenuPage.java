package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MainMenuPage {
    WebDriver driver;
    ArrayList<WebElement> menuList;

    final String menuListXpath = "//*[@class='item menuItems']";
    public MainMenuPage (WebDriver driver){
        this.driver = driver;
        this.menuList = (ArrayList<WebElement>) driver.findElements(By.xpath(menuListXpath));
    }

    public int getMenuItemIndex(String itemName){
        for(int i=0;i<menuList.size();i++) {
            WebElement item = menuList.get(i).findElement(By.xpath("div[1]/p"));
            String text = item.getText();
            if (itemName.equals(text))
                return i;
        }
        return -1;
    }

    public void clickOnMenuItem(int index){
        final String xpath = "div["+index+"]";
        WebElement item = menuList.get(index).findElement(By.xpath(xpath));
        item.click();
    }


}
