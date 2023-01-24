package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;

import static Tools.Utils.timeToWait;

public class MainMenuPage extends BasePage {
    ArrayList<WebElement> menuList;
    final String menuListXpath = "//*[@class='item menuItems']";
    final String itemsXpath = "div[1]/p";

    public MainMenuPage (WebDriver driver){
        super(driver);
        this.menuList = (ArrayList<WebElement>) driver.findElements(By.xpath(menuListXpath));
    }

    public int getMenuItemIndex(String itemName){
        try{
            for(int i=0;i<menuList.size();i++) {
                WebElement item = menuList.get(i).findElement(By.xpath(itemsXpath));
                String text = item.getText();
                if (itemName.equals(text))
                    return i;
            }
            return -1;
        }catch (Exception e){
            System.out.println("error in getting menu item index ");
            e.printStackTrace();
            return -1;
        }

    }

    public void clickOnMenuItem(int index) throws InterruptedException {
        try{
            WebElement item = menuList.get(index);
            item.click();
            timeToWait(1);
        }catch (Exception e){
            System.out.println("error in clicking on menu item ");
            e.printStackTrace();
        }

    }


}
