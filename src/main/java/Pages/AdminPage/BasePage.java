package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    WebDriver driver;
    WebElement ordersPage;
    final String ordersId = "orders";

    public BasePage (WebDriver driver){
        this.driver = driver;
    }

    public void ordersPageClick(){
        this.ordersPage = driver.findElement(By.id(ordersId));
        this.ordersPage.click();
    }



}
